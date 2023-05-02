package com.example.springsecurityjwtredis.model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
//@ToString
@Table(name = "tb_authority", schema = "public")
public class Authority implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static Authority of(Member member, Role role){
        return Authority.builder()
                .role(role)
                .member(member)
                .build();
    }

    public static Authority of(Group group, Role role){
        return Authority.builder()
                .role(role)
                .group(group)
                .build();
    }

    @Override
    public String getAuthority() {
        return role.getRoleName();
    }

}
