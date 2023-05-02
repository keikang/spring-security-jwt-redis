package com.example.springsecurityjwtredis.mapper.memberGroupRel;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.MemberGroupRel;
import com.example.springsecurityjwtredis.model.reponse.MemberGroupRelResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberGroupRelResMapper extends GenericMapper<MemberGroupRelResDto, MemberGroupRel> {
}
