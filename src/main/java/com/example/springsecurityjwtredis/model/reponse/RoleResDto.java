package com.example.springsecurityjwtredis.model.reponse;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResDto {

    private Long roleId;

    private String roleName;

    private String roleDescription;

    private String dataAccessYn;

}
