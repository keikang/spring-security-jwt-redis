package com.example.springsecurityjwtredis.mapper.urlInfo;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.UrlInfo;
import com.example.springsecurityjwtredis.model.request.UrlInfoReqDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlInfoReqMapper extends GenericMapper<UrlInfoReqDto, UrlInfo> {
}
