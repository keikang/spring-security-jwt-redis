package com.example.springsecurityjwtredis.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfoResDto {

    private Long urlid;

    private String urlAddr;

    private String urlRole;
}
