package com.example.springsecurityjwtredis.jwt;

import com.example.springsecurityjwtredis.model.Entity.Authority;
import com.example.springsecurityjwtredis.model.Entity.UrlInfo;
import com.example.springsecurityjwtredis.repository.AuthorityRepository;
import com.example.springsecurityjwtredis.repository.LogoutAccessTokenRedisRepository;
import com.example.springsecurityjwtredis.repository.UrlInfoRepository;
import com.example.springsecurityjwtredis.service.MemberUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final MemberUserDetailService memberUserDetailService;

    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    private final UrlInfoRepository urlInfoRepository;

    private final AuthorityRepository authorityRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter doFilterInternal getRequestURI : "+request.getRequestURI());
        System.out.println("JwtAuthenticationFilter doFilterInternal getContextPath : "+request.getContextPath());
        System.out.println("JwtAuthenticationFilter doFilterInternal getRequestURL : "+request.getRequestURL());


        String accessToken = getToken(request);
        if(accessToken != null){
            checkLogout(accessToken);
            String username = jwtTokenUtil.getUsername(accessToken);
            //System.out.println("JwtAuthenticationFilter doFilterInternal username : "+username);
            if(username != null){
                UserDetails userDetails = memberUserDetailService.loadUserByUsername(username);
                //System.out.println("JwtAuthenticationFilter doFilterInternal userDetails username : "+username);
                equalsUserNameFromTokenAndUserDetails(userDetails.getUsername(), username);
                validateAccessToken(accessToken, userDetails);
                processSecurity(request, userDetails);
            }
        }

        Authentication authentication = null;
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        String memberId = null;
        if(authentication != null){

            memberId = authentication.getName();
            System.out.println("id 존재함 memberId : "+memberId);
            List<Authority> authorityList = authorityRepository.findByMember_MemberId(memberId);
            Optional<UrlInfo> roles = urlInfoRepository.findByUrlAddrLikeQuery(request.getRequestURI());
            //Optional<UrlInfo> roles = urlInfoRepository.findByUrlAddrLike("'\\%"+request.getRequestURI()+"\\%'");
            //Optional<UrlInfo> roles = urlInfoRepository.findByUrlAddrStartsWith("$$"+request.getRequestURI()+"$$");
            if(memberId != null){
                System.out.println("JwtAuthenticationFilter doFilterInternal 유저");
                authorityList.forEach(o-> System.out.println(o.getAuthority()));
                System.out.println("JwtAuthenticationFilter doFilterInternal URL");
                System.out.println(Arrays.toString(roles.stream().toArray()));
                boolean result = authorityList.stream().anyMatch(o -> o.getAuthority().equals(roles.get().getUrlRole()));
                System.out.println("JwtAuthenticationFilter doFilterInternal result : "+result);
                if(!result){
                    throw new IllegalAccessError("응 안되 돌아가");
                }
            }
        }

        System.out.println("넌 자격이 있음");

        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        //System.out.println("JwtAuthenticationFilter getToken headerAuth : "+headerAuth);
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }

    private void checkLogout(String accessToken){
        if(logoutAccessTokenRedisRepository.existsById(accessToken)){
            throw new IllegalArgumentException("이미 로그아웃된 회원");
        }
    }

    private void equalsUserNameFromTokenAndUserDetails(String memberUserDeatailName, String tokenUserName){
        //System.out.println("JwtAuthenticationFilter equalsUserNameFromTokenAndUserDetails memberUserDeatailName : "+memberUserDeatailName);
        //System.out.println("JwtAuthenticationFilter equalsUserNameFromTokenAndUserDetails tokenUserName : "+tokenUserName);
        if(!memberUserDeatailName.equals(tokenUserName)){
            throw new IllegalArgumentException("userName이 토큰 userName과 일치하지 않습니다.");
        }
    }

    private void validateAccessToken(String accessToken, UserDetails userDetails){
        if(!jwtTokenUtil.validateToken(accessToken, userDetails)){
            throw new IllegalArgumentException("토근이 유효하지 않습니다.");
        }
    }

    private void processSecurity(HttpServletRequest request, UserDetails userDetails){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
