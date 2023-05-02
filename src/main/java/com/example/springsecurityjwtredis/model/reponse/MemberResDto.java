package com.example.springsecurityjwtredis.model.reponse;

import lombok.*;

import java.util.ArrayList;
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

    private String email;

    private List<RoleResDto> roleResDtoList = new ArrayList<>();

    private List<GroupResDto> groupResDtoList = new ArrayList<>();
}
