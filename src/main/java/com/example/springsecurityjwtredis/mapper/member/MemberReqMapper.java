package com.example.springsecurityjwtredis.mapper.member;


import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.Member;
import com.example.springsecurityjwtredis.model.request.MemberReqDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberReqMapper extends GenericMapper<MemberReqDto, Member> {
}
