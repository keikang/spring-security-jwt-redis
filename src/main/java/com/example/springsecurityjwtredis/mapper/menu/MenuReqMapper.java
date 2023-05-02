package com.example.springsecurityjwtredis.mapper.menu;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Menu;
import com.example.springsecurityjwtredis.model.request.MenuReqDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuReqMapper extends GenericMapper<MenuReqDto, Menu> {
}
