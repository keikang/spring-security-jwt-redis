package com.example.springsecurityjwtredis.model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tb_group_member_rel", schema = "public")
//@ToString
public class MemberGroupRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupMemberRelId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public static MemberGroupRel of(Member member, Group group){
        MemberGroupRel memberGroupRel = MemberGroupRel.builder()
                .member(member)
                .group(group)
                .build();
        return memberGroupRel;
    }
}
