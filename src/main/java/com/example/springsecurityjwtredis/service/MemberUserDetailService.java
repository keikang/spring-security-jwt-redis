package com.example.springsecurityjwtredis.service;

import com.example.springsecurityjwtredis.config.cache.CacheKey;
import com.example.springsecurityjwtredis.member.MemberUserDetails;
import com.example.springsecurityjwtredis.model.Entity.Member;
import com.example.springsecurityjwtredis.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {

    private final RoleService roleService;

    private final MemberRepository memberRepository;

    @Override
    @Cacheable(value = CacheKey.USER, key = "#username", unless = "#result == null")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("MemberUserDetailService loadUserByUsername memberId : "+username);
        Member member = memberRepository.findByUsernameWithAuthorities(username)
                .orElseThrow(() -> new NoSuchElementException("그런 회원 없습니다."));
        MemberUserDetails userDetails = MemberUserDetails.of(member);
        userDetails.setRoleNames(roleService.getRoleNameList(member.getRoleIds()));
        return userDetails;
    }

    @Cacheable(value = CacheKey.USER, key = "#memberId", unless = "#result == null")
    public MemberUserDetails loadUserByMemberId(String memberId) throws UsernameNotFoundException {
        System.out.println("MemberUserDetailService loadUserByMemberId memberId : "+memberId);
        Member member = memberRepository.findByMemberIdWithAuthorities(memberId)
                .orElseThrow(() -> new NoSuchElementException("그런 회원 없습니다."));
        MemberUserDetails userDetails = MemberUserDetails.of(member);
        userDetails.setRoleNames(roleService.getRoleNameList(member.getRoleIds()));
        return userDetails;
    }

}
