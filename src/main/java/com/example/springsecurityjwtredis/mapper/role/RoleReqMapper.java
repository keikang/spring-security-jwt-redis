package com.example.springsecurityjwtredis.mapper.role;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Role;
import com.example.springsecurityjwtredis.model.request.RoleReqDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleReqMapper extends GenericMapper<RoleReqDto, Role> {
}
