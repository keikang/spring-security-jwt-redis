package com.example.springsecurityjwtredis.model.reponse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {

    private String memberId;

    private String username;

    private List<String> roles;
}
