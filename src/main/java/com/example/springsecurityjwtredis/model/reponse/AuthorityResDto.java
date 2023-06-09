package com.example.springsecurityjwtredis.model.reponse;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityResDto {

    private String memberId;

    private String roleName;

    private MemberResDto memberResDto;

    private RoleResDto roleResDto;

    private String roleDescription;

}
