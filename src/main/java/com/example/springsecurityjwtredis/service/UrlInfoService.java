package com.example.springsecurityjwtredis.service;

import com.example.springsecurityjwtredis.mapper.urlInfo.UrlInfoReqMapper;
import com.example.springsecurityjwtredis.mapper.urlInfo.UrlInfoResMapper;
import com.example.springsecurityjwtredis.model.Entity.Authority;
import com.example.springsecurityjwtredis.model.Entity.UrlInfo;
import com.example.springsecurityjwtredis.model.reponse.AuthorityResDto;
import com.example.springsecurityjwtredis.model.reponse.UrlInfoResDto;
import com.example.springsecurityjwtredis.model.request.AuthorityReqDto;
import com.example.springsecurityjwtredis.model.request.UrlInfoReqDto;
import com.example.springsecurityjwtredis.repository.UrlInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UrlInfoService {

    private final UrlInfoRepository urlInfoRepository;

    private final UrlInfoReqMapper urlInfoReqMapper;
    private final UrlInfoResMapper urlInfoResMapperl;

    public UrlInfoResDto saveUrlInfo(UrlInfoReqDto urlInfoReqDto){
        UrlInfo urlInfo = urlInfoReqMapper.toEntity(urlInfoReqDto);
        UrlInfo urlInfoResult = urlInfoRepository.save(urlInfo);
        UrlInfoResDto urlInfoResDto = urlInfoResMapperl.toDto(urlInfoResult);
        return urlInfoResDto;
    }
}
