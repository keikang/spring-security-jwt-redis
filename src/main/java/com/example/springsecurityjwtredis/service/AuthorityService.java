package com.example.springsecurityjwtredis.service;

import com.example.springsecurityjwtredis.mapper.authority.AuthorityReqMapper;
import com.example.springsecurityjwtredis.mapper.authority.AuthorityResMapper;
import com.example.springsecurityjwtredis.model.Entity.Authority;
import com.example.springsecurityjwtredis.model.reponse.AuthorityResDto;
import com.example.springsecurityjwtredis.model.request.AuthorityReqDto;
import com.example.springsecurityjwtredis.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final AuthorityReqMapper authorityReqMapper;
    private final AuthorityResMapper authorityResMapper;

    public AuthorityResDto saveAuthority(AuthorityReqDto authorityReqDto){
        Authority authority = authorityReqMapper.toEntity(authorityReqDto);
        Authority authorityResult = authorityRepository.save(authority);
        AuthorityResDto authorityResDto = authorityResMapper.toDto(authorityResult);
        return authorityResDto;
    }

    public String deleteAuthority(AuthorityReqDto authorityReqDto){
        //Authority authority = authorityReqMapper.toEntity(authorityReqDto);
        authorityRepository.deleteByMemberMemberIdAndAndRole(authorityReqDto.getMemberId(), authorityReqDto.getRole());
        return authorityReqDto.getMemberId() + " 의 " +  authorityReqDto.getRole() + "이 삭제되었습니다.";
    }
}
