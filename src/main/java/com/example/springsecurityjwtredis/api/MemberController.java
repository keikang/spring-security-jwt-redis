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

    @GetMapping("/list")
    public ResponseEntity<MemberResDto> memberList(){
        System.out.println("MemberController memberList");
        return ResponseEntityFactory.ok(memberService.memberList());
    }

    @PostMapping("/add")
    public ResponseEntity<MemberResDto> addMember(@RequestBody MemberReqDto memberReqDto){
        System.out.println("MemberController addMember");
        return ResponseEntityFactory.ok(memberService.addMember(memberReqDto));
    }

    @PutMapping("/update")
    public ResponseEntity<MemberResDto> updateMember(@RequestBody MemberReqDto memberReqDto){
        System.out.println("MemberController updateMember");
        return ResponseEntityFactory.ok(memberService.updateMember(memberReqDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MemberResDto> deleteMember(@RequestBody MemberReqDto memberReqDto){
        System.out.println("MemberController deleteMember");
        memberService.deleteMember(memberReqDto);
        return ResponseEntityFactory.ok(  memberReqDto.getMemberId() + "가 삭제 되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto loginReqDto){
        System.out.println("MemberController login");
        TokenDto tokenDto = memberService.login(loginReqDto);
        if(tokenDto == null){
            return ResponseEntityFactory.fail(CommonStatusType.LOGIN_FAIL, "");
        }else{
            return ResponseEntityFactory.ok(tokenDto);
        }
    }

    @PostMapping("/detail")
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

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> logout(@RequestHeader("RefreshToken") String refreshToken){
        return ResponseEntityFactory.ok(memberService.reissueAccessToken(refreshToken));
    }

    public String getToken(String accessToken){
        return accessToken.substring(7);
    }

    //TODO refreshToken을 사용하여 accessToken 갱신하는 로직 필요

    //TODO myinfo 같은 기능 개발 필요
}
