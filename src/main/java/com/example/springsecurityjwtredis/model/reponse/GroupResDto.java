package com.example.springsecurityjwtredis.model.reponse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResDto {

    private Long groupId;

    private String groupName;

    private String groupDescription;

    private List<RoleResDto> roleResDtoList;
}
