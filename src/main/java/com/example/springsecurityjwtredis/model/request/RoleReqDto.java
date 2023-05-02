package com.example.springsecurityjwtredis.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleReqDto {

    private Long roleId;

    private String roleName;

    private String roleDescription;

    private String dataAccessYn;

}
