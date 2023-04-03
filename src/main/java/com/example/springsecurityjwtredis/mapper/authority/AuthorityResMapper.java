package com.example.springsecurityjwtredis.mapper.authority;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Authority;
import com.example.springsecurityjwtredis.model.reponse.AuthorityResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorityResMapper extends GenericMapper<AuthorityResDto, Authority> {

    @Mapping(source = "member.memberId", target = "memberId")
    AuthorityResDto toDto(Authority authority);
}
