package com.example.springsecurityjwtredis.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityReqDto {

    private long authorityId;

    private String memberId;

    private Long roleId;

    //@PageableDefault(size = 10, sort = "authority_id", direction = Sort.Direction.DESC)
    private Pageable pageable;
}
