package com.example.springsecurityjwtredis.service;

import com.example.springsecurityjwtredis.mapper.menu.MenuReqMapper;
import com.example.springsecurityjwtredis.mapper.menu.MenuResMapper;
import com.example.springsecurityjwtredis.model.Entity.Menu;
import com.example.springsecurityjwtredis.model.reponse.MenuResDto;
import com.example.springsecurityjwtredis.model.request.MenuReqDto;
import com.example.springsecurityjwtredis.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final RoleService roleService;

    private final MenuRepository menuRepository;

    private final MenuReqMapper menuReqMapper;

    private final MenuResMapper menuResMapperl;

    public MenuResDto addMenu(MenuReqDto menuReqDto){
        Menu menu = menuReqMapper.toEntity(menuReqDto);
        Menu menuResult = menuRepository.save(menu);
        MenuResDto menuResDto = menuResMapperl.toDto(menuResult);
        menuResDto.setMenuRole(roleService.getRole(menuResult.getRoleId()).getRoleName());
        return menuResDto;
    }
}
