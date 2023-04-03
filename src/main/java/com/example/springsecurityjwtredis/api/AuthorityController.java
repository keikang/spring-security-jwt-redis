package com.example.springsecurityjwtredis.api;

import com.example.springsecurityjwtredis.model.ResponseEntityFactory;
import com.example.springsecurityjwtredis.model.reponse.AuthorityResDto;
import com.example.springsecurityjwtredis.model.request.AuthorityReqDto;
import com.example.springsecurityjwtredis.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping("/")
    public ResponseEntity<AuthorityResDto> saveAuthority(@RequestBody AuthorityReqDto authorityReqDto){
        System.out.println("AuthorityController saveAuthority");
        return ResponseEntityFactory.ok(authorityService.saveAuthority(authorityReqDto));
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAuthority(@RequestBody AuthorityReqDto authorityReqDto){
        System.out.println("AuthorityController deleteAuthority");
        return ResponseEntityFactory.ok(authorityService.deleteAuthority(authorityReqDto));
    }
}
