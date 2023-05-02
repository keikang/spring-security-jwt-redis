package com.example.springsecurityjwtredis.mapper.group;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Group;
import com.example.springsecurityjwtredis.model.reponse.GroupResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupResMapper extends GenericMapper<GroupResDto, Group> {
}
