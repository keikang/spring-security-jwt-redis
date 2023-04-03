package com.example.springsecurityjwtredis.model.reponse;

import com.example.springsecurityjwtredis.model.Entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityResDto {

    private String memberId;

    private String role;

    private String roleDescription;

}
