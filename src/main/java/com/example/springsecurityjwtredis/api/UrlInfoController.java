package com.example.springsecurityjwtredis.api;

import com.example.springsecurityjwtredis.model.ResponseEntityFactory;
import com.example.springsecurityjwtredis.model.reponse.AuthorityResDto;
import com.example.springsecurityjwtredis.model.request.UrlInfoReqDto;
import com.example.springsecurityjwtredis.service.UrlInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urlInfo")
public class UrlInfoController {

    private final UrlInfoService urlInfoService;

    @PostMapping("/")
    public ResponseEntity<AuthorityResDto> saveUrlInfo(@RequestBody UrlInfoReqDto urlInfoReqDto){
        System.out.println("UrlInfoController saveUrlInfo");
        return ResponseEntityFactory.ok(urlInfoService.saveUrlInfo(urlInfoReqDto));
    }
}
