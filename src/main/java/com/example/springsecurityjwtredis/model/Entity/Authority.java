package com.example.springsecurityjwtredis.model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "tb_authority", schema = "public")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorityId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String role;

    private String roleDescription;

    public static Authority ofDefault(Member member){
        return Authority.builder()
                .role("DEFAULT")
                .member(member)
                .build();
    }

    public static Authority ofAdmin(Member member){
        return Authority.builder()
                //.role("ROLE_ADMIN")
                .member(member)
                .build();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
