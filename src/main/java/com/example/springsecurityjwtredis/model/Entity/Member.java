package com.example.springsecurityjwtredis.model.Entity;


import com.example.springsecurityjwtredis.model.request.MemberReqDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor()
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tb_member", schema = "public")
@ToString
public class Member {

    @Id
    private String memberId;

    private String password;

    private String username;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
    //@OneToMany
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    public static Member ofUser(MemberReqDto memberReqDto){
        Member member = Member.builder()
                .memberId(memberReqDto.getMemberId())
                .password(memberReqDto.getPassword())
                .username(memberReqDto.getUsername())
                .build();
        member.addAuthority(Authority.ofDefault(member));
        return member;
    }

    public static Member ofAdmin(MemberReqDto memberReqDto){
        Member member = Member.builder()
                .memberId(memberReqDto.getMemberId())
                .password(memberReqDto.getPassword())
                .username(memberReqDto.getUsername())
                .build();
        //member.addAuthority(Authority.ofAdmin(member));
        return member;
    }

    private void addAuthority(Authority authority){
        authorities.add(authority);
    }

    public List<String> getRoles(){
        return authorities.stream()
                .map(Authority::getRole)
                .collect(Collectors.toUnmodifiableList());
    }
}
