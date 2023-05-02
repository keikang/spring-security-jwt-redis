package com.example.springsecurityjwtredis.mapper.group;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Group;
import com.example.springsecurityjwtredis.model.request.GroupReqDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupReqMapper extends GenericMapper<GroupReqDto, Group> {
}
