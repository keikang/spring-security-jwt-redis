package com.example.springsecurityjwtredis.api;

import com.example.springsecurityjwtredis.model.ResponseEntityFactory;
import com.example.springsecurityjwtredis.model.reponse.AuthorityResDto;
import com.example.springsecurityjwtredis.model.request.MenuReqDto;
import com.example.springsecurityjwtredis.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/add")
    public ResponseEntity<AuthorityResDto> addMenu(@RequestBody MenuReqDto menuReqDto){
        System.out.println("MenuController addUrlInfo");
        return ResponseEntityFactory.ok(menuService.addMenu(menuReqDto));
    }
}
