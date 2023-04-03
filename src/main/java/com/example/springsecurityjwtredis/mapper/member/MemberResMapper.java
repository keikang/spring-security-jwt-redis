package com.example.springsecurityjwtredis.mapper.member;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Member;
import com.example.springsecurityjwtredis.model.reponse.MemberResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberResMapper extends GenericMapper<MemberResDto, Member> {
}
