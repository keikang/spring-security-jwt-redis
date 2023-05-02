package com.example.springsecurityjwtredis.mapper.menu;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Menu;
import com.example.springsecurityjwtredis.model.reponse.MenuResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuResMapper extends GenericMapper<MenuResDto, Menu> {
}
