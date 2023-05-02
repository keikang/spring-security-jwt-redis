package com.example.springsecurityjwtredis.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupReqDto {

    private Long groupId;

    private String groupName;

    private String groupDescription;

    List<Long> roleIdList;
}
