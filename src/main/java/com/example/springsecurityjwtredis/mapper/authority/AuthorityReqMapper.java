package com.example.springsecurityjwtredis.mapper.authority;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Authority;
import com.example.springsecurityjwtredis.model.request.AuthorityReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorityReqMapper extends GenericMapper<AuthorityReqDto, Authority> {

    @Mapping(source = "memberId", target = "member.memberId")
    Authority toEntity(AuthorityReqDto authorityReqDto);
}
