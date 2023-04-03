package com.example.springsecurityjwtredis.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfoReqDto {

    private String urlAddr;

    private String urlRole;
}
