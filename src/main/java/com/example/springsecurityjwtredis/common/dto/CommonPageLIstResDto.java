package com.example.springsecurityjwtredis.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonPageLIstResDto {
    private List<?> pageList;

    private Integer startBlockPage;

    private Integer endBlockPage;
}
