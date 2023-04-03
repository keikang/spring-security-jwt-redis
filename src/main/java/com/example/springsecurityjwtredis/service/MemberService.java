package com.example.springsecurityjwtredis.service;


import com.example.springsecurityjwtredis.jwt.JwtExpirationEnums;
import com.example.springsecurityjwtredis.jwt.JwtTokenUtil;
import com.example.springsecurityjwtredis.mapper.member.MemberResMapper;
import com.example.springsecurityjwtredis.model.Entity.Member;
import com.example.springsecurityjwtredis.model.request.LoginReqDto;
import com.example.springsecurityjwtredis.model.request.MemberReqDto;
import com.example.springsecurityjwtredis.model.dto.TokenDto;
import com.example.springsecurityjwtredis.model.redis.RefreshToken;
import com.example.springsecurityjwtredis.model.reponse.MemberResDto;
import com.example.springsecurityjwtredis.repository.LogoutAccessTokenRedisRepository;
import com.example.springsecurityjwtredis.repository.RefreshTokenRedisRepository;
import com.example.springsecurityjwtredis.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final MemberResMapper memberResMapper;

    public MemberResDto saveUser(MemberReqDto memberReqDto){
        System.out.println("MemberService saveUser");
        //memberReqDto.setPassword(passwordEncoder.encode(memberReqDto.getPassword()));
        //memberReqDto.setPassword(memberReqDto.getPassword());
        System.out.println("MemberService saveUser ofUser :"+Member.ofUser(memberReqDto).getAuthorities().toString());
        Member member = memberRepository.save(Member.ofUser(memberReqDto));
        MemberResDto memberResDto = memberResMapper.toDto(member);
        return memberResDto;
    }

    public void saveAdmin(MemberReqDto memberReqDto){
        //memberReqDto.setPassword(passwordEncoder.encode(memberReqDto.getPassword()));
        memberReqDto.setPassword(memberReqDto.getPassword());
        memberRepository.save(Member.ofAdmin(memberReqDto));
    }

    public TokenDto login(LoginReqDto loginReqDto){
/*        Member member = memberRepository.findById(loginReqDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("회원 없습니다."));*/
        //checkPassword(loginReqDto.getMemberId(), member.getMemberId());
        Optional<Member> member = memberRepository.findById(loginReqDto.getMemberId());

        if(member.isPresent()){
            Member memberResult = member.get();
            String memberId = memberResult.getMemberId();
            String accessToken = jwtTokenUtil.generateAccessToken(memberId);
            RefreshToken refreshToken = saveRefreshToken(memberId);
            return TokenDto.of(accessToken, refreshToken.getRefreshToken());
        }else{
            return null;
        }

    }

    private void checkPassword(String inputPw, String memberPw) {
        if(!passwordEncoder.matches(inputPw, memberPw)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private RefreshToken saveRefreshToken(String memeberId){
        System.out.println("MemberService saveRefreshToken memeberId : "+memeberId);
        return refreshTokenRedisRepository.save(
                    RefreshToken.createRefreshToken(memeberId
                                                    , jwtTokenUtil.generateRefreshToken(memeberId)
                                                    , JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getTimeValue())
                );
    }

    public MemberResDto getMemberRes(String memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("잘못된 id"));
        System.out.println("MemberService getMemberRes member : "+member);
        //System.out.println("MemberService getMemberRes getCurrentMemberId : "+getCurrentMemberId());
        //System.out.println("MemberService getMemberRes member getMemberId  : "+member.getMemberId());
/*        if(!member.getMemberId().equals(getCurrentMemberId())){
            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
        }*/
        MemberResDto memberResDto = memberResMapper.toDto(member);
        memberResDto.setRoles(member.getRoles());
        //System.out.println("MemberService getMemberRes memberRES : "+memberResMapper.toDto(member));
        return memberResDto;
    }

    private String getCurrentMemberId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }
}
