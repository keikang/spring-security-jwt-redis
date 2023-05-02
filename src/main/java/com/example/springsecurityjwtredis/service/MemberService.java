package com.example.springsecurityjwtredis.service;



import com.example.springsecurityjwtredis.common.dto.CommonPageLIstResDto;
import com.example.springsecurityjwtredis.common.utils.Utils;
import com.example.springsecurityjwtredis.jwt.JwtExpirationEnums;
import com.example.springsecurityjwtredis.jwt.JwtTokenUtil;
import com.example.springsecurityjwtredis.mapper.group.GroupResMapper;
import com.example.springsecurityjwtredis.mapper.member.MemberReqMapper;
import com.example.springsecurityjwtredis.mapper.member.MemberResMapper;
import com.example.springsecurityjwtredis.member.MemberUserDetails;
import com.example.springsecurityjwtredis.model.Entity.Member;
import com.example.springsecurityjwtredis.model.dto.TokenDto;
import com.example.springsecurityjwtredis.model.redis.LogoutAccessToken;
import com.example.springsecurityjwtredis.model.redis.RefreshToken;
import com.example.springsecurityjwtredis.model.reponse.MemberResDto;
import com.example.springsecurityjwtredis.model.request.LoginReqDto;
import com.example.springsecurityjwtredis.model.request.MemberReqDto;
import com.example.springsecurityjwtredis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //private final RoleRepository roleRepository;

    private final RoleService roleService;

    private final GroupService groupService;

    private final MemberGroupRelRepository memberGroupRelRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final MemberReqMapper memberReqMapper;

    private final MemberResMapper memberResMapper;

    private final GroupResMapper groupResMapper;

    public CommonPageLIstResDto memberList(){
        System.out.println("MemberService memberList");
        List<Member> memberEntityList = memberRepository.findAll();
        //memberEntityList.forEach(v-> System.out.println(v.getRoleIds()));
        //List<MemberResDto> memberResDtoList = memberResMapper.toDtoList(memberEntityList);
        List<MemberResDto> memberResDtoList = new ArrayList<>();
        for (Member member : memberEntityList) {
            MemberResDto obj = memberResMapper.toDto(member);
            obj.setGroupResDtoList(groupService.getGroupResList(member.getGroupIds()));
            memberResDtoList.add(obj);
        }

        CommonPageLIstResDto result = CommonPageLIstResDto.builder()
                .pageList(memberResDtoList)
                .build();

        return result;
    }

    public MemberResDto addMember(MemberReqDto memberReqDto){
        //System.out.println("MemberService addMember");
        Member member = Member.ofUser(memberReqDto);

        System.out.println("MemberService saveUser getRoleIdList isNullOrEmpty result : "+ Utils.isNullOrEmpty(memberReqDto.getRoleIdList()));
        if(!Utils.isNullOrEmpty(memberReqDto.getRoleIdList())){
            memberReqDto.getRoleIdList().forEach(r->member.addAuthority(member, roleService.getRole(r)));
        }else{
            memberReqDto.getRoleIdList().forEach(r->member.addAuthority(member, roleService.getRole(2L)));
        }

        System.out.println("MemberService saveUser getGroupIdList isNullOrEmpty result : "+Utils.isNullOrEmpty(memberReqDto.getGroupIdList()));
        if(!Utils.isNullOrEmpty(memberReqDto.getGroupIdList())){
            memberReqDto.getGroupIdList().forEach(g->member.addMemberGroupRel(member, groupService.getGroup(g)));
        }

        //member.addAuthority(member, memberReqDto.getRoles());
        Member result = memberRepository.save(member);
        //System.out.println("MemberService saveUser result tostring : "+result);
        MemberResDto memberResDto = memberResMapper.toDto(result);
        memberResDto.setGroupResDtoList(groupService.getGroupResList(result.getGroupIds()));
        return memberResDto;
    }

    public MemberResDto updateMember(MemberReqDto memberReqDto){
        System.out.println("MemberService updateMember memberId : "+memberReqDto.getMemberId());
        authorityRepository.deleteByMemberMemberId(memberReqDto.getMemberId());
        authorityRepository.flush();

        memberGroupRelRepository.deleteByMemberMemberId(memberReqDto.getMemberId());
        memberGroupRelRepository.flush();

        Member member = memberRepository.findByMemberId(memberReqDto.getMemberId()).orElseThrow(() -> new NoSuchElementException("해당하는 Member가 없습니다."));
        memberReqMapper.updateFromDto(memberReqDto, member);
        //System.out.println("MemberService updateMember member22222 : "+member);

        System.out.println("MemberService saveUser getRoleIdList isNullOrEmpty result : "+Utils.isNullOrEmpty(memberReqDto.getRoleIdList()));
        if(!Utils.isNullOrEmpty(memberReqDto.getRoleIdList())){
            memberReqDto.getRoleIdList().forEach(r->member.addAuthority(member, roleService.getRole(r)));
        }else{
            System.out.println("role이 아예 없으면 default 권한 삽입");
            member.addAuthority(member, roleService.getRole("DEFAULT"));
        }

        System.out.println("MemberService saveUser getGroupIdList isNullOrEmpty result : "+Utils.isNullOrEmpty(memberReqDto.getGroupIdList()));
        if(!Utils.isNullOrEmpty(memberReqDto.getGroupIdList())){
            memberReqDto.getGroupIdList().forEach(g->member.addMemberGroupRel(member, groupService.getGroup(g)));
        }

        Member result = memberRepository.save(member);

        //System.out.println("MemberService updateMember Member entity : "+result);
        MemberResDto memberResDto = memberResMapper.toDto(result);
        memberResDto.setGroupResDtoList(groupService.getGroupResList(result.getGroupIds()));
        //System.out.println("MemberService updateMember Member memberResDto : "+memberResDto);
        //memberResDto.setRoles(roleService.getRoleNameList(result.getRoleIds()));
        return memberResDto;
    }

    public void deleteMember(MemberReqDto memberReqDto){
        //System.out.println("MemberService deleteMember");
        authorityRepository.deleteByMemberMemberId(memberReqDto.getMemberId());
        memberGroupRelRepository.deleteByMemberMemberId(memberReqDto.getMemberId());
        memberRepository.deleteById(memberReqDto.getMemberId());
    }

    public MemberResDto getMemberRes(String memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("잘못된 id"));
        //System.out.println("MemberService getMemberRes member : "+member);
        MemberResDto memberResDto = memberResMapper.toDto(member);
        memberResDto.setGroupResDtoList(groupService.getGroupResList(member.getGroupIds()));
        //System.out.println("MemberService getMemberRes memberRES : "+memberResMapper.toDto(member));
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
        //
        Optional<Member> member = memberRepository.findByMemberIdAndAndPassword(loginReqDto.getMemberId(), loginReqDto.getPassword());

        if(member.isPresent()){
            Member memberResult = member.get();
            //checkPassword(loginReqDto.getMemberId(), memberResult.getMemberId());
            String memberId = memberResult.getMemberId();
            String accessToken = jwtTokenUtil.generateAccessTokenByMemberId(memberId);
            RefreshToken refreshToken = saveRefreshToken(memberId);
            return TokenDto.of(accessToken, refreshToken.getRefreshToken());
        }else{
            return null;
        }

    }

    public void logout(TokenDto tokenDto, String memberId){
        String accessToken = getToken(tokenDto.getAcessToken());
        System.out.println("MemberService logout accessToken : "+accessToken);
        long remainTimeMilliseconds = jwtTokenUtil.getRemainMilliSeconds(accessToken);
        System.out.println("MemberService logout remainTimeMilliseconds : "+remainTimeMilliseconds);
        refreshTokenRedisRepository.deleteById(memberId);
        logoutAccessTokenRedisRepository.save(LogoutAccessToken.of(accessToken, memberId, remainTimeMilliseconds));
    }

    public String getToken(String accessToken){
        return accessToken.substring(7);
    }

    private void checkPassword(String inputPw, String memberPw) {
        if(!passwordEncoder.matches(inputPw, memberPw)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private RefreshToken saveRefreshToken(String memeberId){
        //System.out.println("MemberService saveRefreshToken memeberId : "+memeberId);
        return refreshTokenRedisRepository.save(
                    RefreshToken.createRefreshToken(memeberId
                                                    , jwtTokenUtil.generateRefreshTokenByMemberId(memeberId)
                                                    , JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getTimeValue())
                );
    }

    private String getCurrentMemberId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("MemberService getCurrentMemberId authentication : "+authentication);
        System.out.println("JwtAuthenticationFilter doFilterInternal authentication.getPrincipal() : "+authentication.getPrincipal());
        MemberUserDetails principal = (MemberUserDetails) authentication.getPrincipal();
        return principal.getMemberId();
    }

    public TokenDto reissueAccessToken(String refreshToken){
        String loginMemberId = jwtTokenUtil.getMemberId(refreshToken);
        System.out.println("MemberService reissueAccessTokenByRefreshToken loginMemberId : "+loginMemberId);
        RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(loginMemberId).orElseThrow(NoSuchElementException::new);
        System.out.println("MemberService reissueAccessTokenByRefreshToken reissueAccessToken : "+refreshToken);
        System.out.println("MemberService reissueAccessTokenByRefreshToken redisRefreshToken : "+redisRefreshToken.getRefreshToken());
        if(redisRefreshToken.getRefreshToken().equals(refreshToken)){
            return reissueAccessTokenByRefreshToken(refreshToken, loginMemberId);
        }else{
            throw  new IllegalArgumentException("RefreshToken이 유효하지 않습니다.");
        }

    }

    public TokenDto reissueAccessTokenByRefreshToken(String refreshToken, String memberId){

        long refreshTokenRemainTime = jwtTokenUtil.getRemainMilliSeconds(refreshToken);
        if(refreshTokenRemainTime > 1000) {
            String accessToken = jwtTokenUtil.generateAccessTokenByMemberId(memberId);
            //return TokenDto.of(accessToken, saveRefreshToken(memberId).getRefreshToken());
            return TokenDto.of(accessToken, refreshToken);
        }else{
            throw  new IllegalArgumentException("RefreshToken이 만료됐습니다.");
        }
    }

/*    public List<String> getRoleNameList(List<Long> roleIdList){

        return roleIdList.stream().map(r -> {
            return roleRepository.getReferenceById(r).getRoleName();
        }).collect(Collectors.toList());
    }*/
}
