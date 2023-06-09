package com.example.springsecurityjwtredis.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberReqDto {

    private String memberId;

    private String password;

    private String username;

    private String email;

    List<Long> roleIdList;

    List<Long> groupIdList;

}
