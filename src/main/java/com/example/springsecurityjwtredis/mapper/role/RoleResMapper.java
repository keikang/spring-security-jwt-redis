package com.example.springsecurityjwtredis.mapper.role;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Role;
import com.example.springsecurityjwtredis.model.reponse.RoleResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleResMapper extends GenericMapper<RoleResDto, Role> {
}
