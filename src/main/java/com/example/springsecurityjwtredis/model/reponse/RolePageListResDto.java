package com.example.springsecurityjwtredis.model.reponse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePageListResDto {

    private List<RoleResConverter> pageList;
    //private Page<RoleResConverter> pageList;

    private Integer startBlockPage;

    private Integer endBlockPage;
}
