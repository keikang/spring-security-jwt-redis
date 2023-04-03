package com.example.springsecurityjwtredis.mapper.urlInfo;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.model.Entity.UrlInfo;
import com.example.springsecurityjwtredis.model.reponse.UrlInfoResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlInfoResMapper extends GenericMapper<UrlInfoResDto, UrlInfo> {
}
