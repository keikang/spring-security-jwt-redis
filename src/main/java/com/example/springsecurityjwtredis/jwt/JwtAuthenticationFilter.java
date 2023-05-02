package com.example.springsecurityjwtredis.jwt;

import com.example.springsecurityjwtredis.member.MemberUserDetails;
import com.example.springsecurityjwtredis.model.Entity.Menu;
import com.example.springsecurityjwtredis.model.Entity.Role;
import com.example.springsecurityjwtredis.repository.LogoutAccessTokenRedisRepository;
import com.example.springsecurityjwtredis.repository.MenuRepository;
import com.example.springsecurityjwtredis.service.GroupService;
import com.example.springsecurityjwtredis.service.MemberUserDetailService;
import com.example.springsecurityjwtredis.service.RoleService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final MemberUserDetailService memberUserDetailService;

    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    private final MenuRepository menuRepository;

    private final RoleService roleService;

    private final GroupService groupService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter doFilterInternal getRequestURI : "+request.getRequestURI());
        System.out.println("JwtAuthenticationFilter doFilterInternal getContextPath : "+request.getContextPath());
        System.out.println("JwtAuthenticationFilter doFilterInternal getRequestURL : "+request.getRequestURL());

        String accessToken = getToken(request);
        System.out.println("JwtAuthenticationFilter doFilterInternal accessToken : "+accessToken);
        if(accessToken != null){
            checkLogout(accessToken);
            String memberId = jwtTokenUtil.getMemberId(accessToken);
            //System.out.println("JwtAuthenticationFilter doFilterInternal username : "+username);
            if(memberId != null){
                MemberUserDetails memberUserDetails = memberUserDetailService.loadUserByMemberId(memberId);
                //System.out.println("JwtAuthenticationFilter doFilterInternal userDetails username : "+username);
                equalsMemberIDFromTokenAndUserDetails(memberUserDetails.getMemberId(), memberId);
                validateAccessToken(accessToken, memberUserDetails);
                processSecurity(request, memberUserDetails);
            }
        }

        Authentication authentication = null;
        System.out.println("JwtAuthenticationFilter doFilterInternal accessToken : "+accessToken);
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            System.out.println("JwtAuthenticationFilter doFilterInternal getAuthentication null 아님");
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        MemberUserDetails memberUserDetails = null;
        if(authentication != null){

            memberUserDetails = (MemberUserDetails) authentication.getPrincipal();
            System.out.println("id 존재함 memberId : "+memberUserDetails.getMemberId());
            //List<Authority> authorityList = authorityRepository.findByMember_MemberId(memberUserDetails.getMemberId());
            Optional<Menu> menuRoles = menuRepository.findByMenuUrlLikeQuery(request.getRequestURI());
            //Optional<UrlInfo> roles = urlInfoRepository.findByUrlAddrLike("'\\%"+request.getRequestURI()+"\\%'");
            //Optional<UrlInfo> roles = urlInfoRepository.findByUrlAddrStartsWith("$$"+request.getRequestURI()+"$$");
            if(authentication.getAuthorities() != null){
                boolean result = false;

                List<String> userRoleList = authentication.getAuthorities().stream().map(o -> {
                    return o.getAuthority();
                }).collect(Collectors.toList());

                List<String> groupRoleList = groupService.getGroupNameListByMemberId(memberUserDetails.getMemberId());


                List<String> userGroupRoleList = new ArrayList<>();
                userGroupRoleList.addAll(userRoleList);
                userGroupRoleList.addAll(groupRoleList);

                userGroupRoleList = userGroupRoleList.stream().distinct().collect(Collectors.toList());

                System.out.println("JwtAuthenticationFilter user가 갖고 있는 Role list");
                System.out.println(userGroupRoleList.toString());

                if(userGroupRoleList.stream().anyMatch(o -> o.equals("ADMIN"))){
                    System.out.println("JwtAuthenticationFilter doFilterInternal ADMIN 권한을 갖고 있을 경우 menu 권한체크는 생략한다.");
                    //filterChain.doFilter(request, response);
                    result = true;
                }else{
                    System.out.println("JwtAuthenticationFilter doFilterInternal ADMIN 권한이 없는 일반유저의 경우 menu Role과 비교");
                    System.out.println("JwtAuthenticationFilter doFilterInternal menu Role");
                    System.out.println(Arrays.toString(menuRoles.stream().toArray()));
                    Role menuRole = roleService.getRole(menuRoles.get().getRoleId());

                    result = userGroupRoleList.stream().anyMatch(o -> o.equals(menuRole.getRoleName()));
                    //result = authentication.getAuthorities().stream().anyMatch(o -> o.getAuthority().equals(menuRole.getRoleName()));
                }

                System.out.println("JwtAuthenticationFilter doFilterInternal result : "+result);

                if(!result){
                    throw new IllegalAccessError("해당하는 권한을 갖고 있지 않습니다.");
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

    private void equalsMemberIDFromTokenAndUserDetails(String memberUserDeatailName, String tokenUserName){
        //System.out.println("JwtAuthenticationFilter equalsUserNameFromTokenAndUserDetails memberUserDeatailName : "+memberUserDeatailName);
        //System.out.println("JwtAuthenticationFilter equalsUserNameFromTokenAndUserDetails tokenUserName : "+tokenUserName);
        if(!memberUserDeatailName.equals(tokenUserName)){
            throw new IllegalArgumentException("memberId이 토큰 memberId와 일치하지 않습니다.");
        }
    }

    private void validateAccessToken(String accessToken, MemberUserDetails memberUserDetails){
        if(!jwtTokenUtil.validateTokenByMemberId(accessToken, memberUserDetails)){
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
