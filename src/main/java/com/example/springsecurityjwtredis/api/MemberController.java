package com.example.springsecurityjwtredis.api;

import com.example.springsecurityjwtredis.jwt.JwtTokenUtil;
import com.example.springsecurityjwtredis.model.ResponseEntityFactory;
import com.example.springsecurityjwtredis.model.dto.TokenDto;
import com.example.springsecurityjwtredis.model.enums.CommonStatusType;
import com.example.springsecurityjwtredis.model.reponse.MemberResDto;
import com.example.springsecurityjwtredis.model.request.LoginReqDto;
import com.example.springsecurityjwtredis.model.request.MemberReqDto;
import com.example.springsecurityjwtredis.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/")
    public String memberMain(){
        System.out.println("MemberController saveUser");
        return "아무 의미없는";
    }

    @PostMapping("/user")
    public ResponseEntity<MemberResDto> saveUser(@RequestBody MemberReqDto memberReqDto){
        System.out.println("MemberController saveUser");
        memberService.saveUser(memberReqDto);
        return ResponseEntityFactory.ok(memberService.saveUser(memberReqDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto loginReqDto){
        System.out.println("MemberController login");
        TokenDto tokenDto = memberService.login(loginReqDto);
        if(tokenDto == null){
            return ResponseEntityFactory.fail(CommonStatusType.LOGIN_FAIL, null);
        }else{
            return ResponseEntityFactory.ok(tokenDto);
        }
    }

    @PostMapping("/getMember")
    public ResponseEntity<MemberResDto> getMember(@RequestBody MemberReqDto memberReqDto){
    //public MemberResDto getMember(@PathVariable String memberId){
        System.out.println("MemberController getMember");
        return ResponseEntityFactory.ok(memberService.getMemberRes(memberReqDto.getMemberId()));
        //return memberService.getMemberRes(memberId);
    }

    @GetMapping("/logout")
    public void logout(@RequestHeader("Authorization") String accessToken, @RequestHeader("RefreshToken") String refreshToken){
        String memberId = jwtTokenUtil.getMemberId(getToken(accessToken));
        memberService.logout(TokenDto.of(accessToken, refreshToken), memberId);
    }

    public String getToken(String accessToken){
        return accessToken.substring(7);
    }

}
