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
@Setter
@NoArgsConstructor()
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tb_member", schema = "public")
//@ToString
public class Member {

    @Id
    private String memberId;

    private String password;

    private String username;

    private String email;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
    @Builder.Default
    private Set<MemberGroupRel> memberGroupRels = new HashSet<>();

    public static Member ofUser(MemberReqDto memberReqDto){
        Member member = Member.builder()
                .memberId(memberReqDto.getMemberId())
                .password(memberReqDto.getPassword())
                .username(memberReqDto.getUsername())
                .email(memberReqDto.getEmail())
                .build();

        //memberReqDto.getRoles().forEach(v ->  member.addAuthority(Authority.of(v, member)));
        //System.out.println("Member ofUser member toString : "+member);
        return member;
    }

    public static Member ofAdmin(MemberReqDto memberReqDto){
        Member member = Member.builder()
                .memberId(memberReqDto.getMemberId())
                .password(memberReqDto.getPassword())
                .username(memberReqDto.getUsername())
                .email(memberReqDto.getEmail())
                .build();
        //member.addAuthority(Authority.ofAdmin(member));
        return member;
    }

    public void addAuthority(Member member, Role role){
        authorities.add(Authority.of(member, role));
    }

    public void addMemberGroupRel(Member member, Group group){
        memberGroupRels.add(MemberGroupRel.of(member, group));
    }

    public List<Long> getRoleIds(){
        return authorities.stream()
                .map(Authority::getRole)
                .map(Role::getRoleId)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Long> getGroupIds(){
        return memberGroupRels.stream()
                .map(MemberGroupRel::getGroup)
                .map(Group::getGroupId)
                .collect(Collectors.toUnmodifiableList());
    }
}
