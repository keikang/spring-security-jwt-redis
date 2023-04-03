package com.example.springsecurityjwtredis.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReqDto {

    private String memberId;

    private String password;
}
