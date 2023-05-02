package com.example.springsecurityjwtredis.service;

import com.example.springsecurityjwtredis.common.dto.CommonPageLIstResDto;
import com.example.springsecurityjwtredis.mapper.role.RoleReqMapper;
import com.example.springsecurityjwtredis.mapper.role.RoleResMapper;
import com.example.springsecurityjwtredis.model.Entity.Role;
import com.example.springsecurityjwtredis.model.reponse.RoleResConverter;
import com.example.springsecurityjwtredis.model.reponse.RoleResDto;
import com.example.springsecurityjwtredis.model.request.RoleReqDto;
import com.example.springsecurityjwtredis.repository.AuthorityRepository;
import com.example.springsecurityjwtredis.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final AuthorityRepository authorityRepository;

    private final RoleReqMapper roleReqMapper;

    private final RoleResMapper roleResMapper;

    public CommonPageLIstResDto getRolesList(Pageable pageable){
        System.out.println("RoleService getRolesList pageable sort : "+pageable.getSort().toString());
        //TODO 현재는 front 솔루션에서 페이징 구현이 되있으므로 사용하지 않음, 향후 성능이슈 발생시 대체
        //List<RoleResConverter> pageList = roleRepository.rolePageList(pageable);
        List<RoleResConverter> totalList = roleRepository.findTotalList();

        int currentPage = pageable.getPageNumber();

        int totalPage = (totalList.size() / pageable.getPageSize());

        int pageBlock = 2;
        int startBlockPage = ((currentPage) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock + 1;
        endBlockPage = totalPage < endBlockPage ? totalPage : endBlockPage;

        return CommonPageLIstResDto.builder()
                .pageList(totalList)
                .startBlockPage(startBlockPage)
                .endBlockPage(endBlockPage)
                .build();
    }
/*    public RolePageListResDto getRolesList(AuthorityReqDto authorityReqDto, Pageable pageable){
        System.out.println("AuthorityService getAuthorityList pageable sort : "+pageable.getSort().toString());
        //TODO 현재는 front 솔루션에서 페이징 구현이 되있으므로 사용하지 않음, 향후 성능이슈 발생시 대체
        //List<RoleResConverter> pageList = roleRepository.rolePageList(pageable);
        List<RoleResConverter> totalList = roleRepository.findTotalList();

        int currentPage = pageable.getPageNumber();

        int totalPage = (totalList.size() / pageable.getPageSize());

        int pageBlock = 2;
        int startBlockPage = ((currentPage) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock + 1;
        endBlockPage = totalPage < endBlockPage ? totalPage : endBlockPage;

        return RolePageListResDto.builder()
                .pageList(totalList)
                .startBlockPage(startBlockPage)
                .endBlockPage(endBlockPage)
                .build();
    }*/

    public RoleResDto addRole(RoleReqDto roleReqDto){
        Role role = roleReqMapper.toEntity(roleReqDto);
        Role result = roleRepository.save(role);
        RoleResDto roleResDto = roleResMapper.toDto(result);
        return roleResDto;
    }

    public void deleteRole(RoleReqDto roleReqDto){
        System.out.println("RoleService deleteRole");
        authorityRepository.deleteByRoleRoleId(roleReqDto.getRoleId());
        authorityRepository.flush();
        roleRepository.deleteById(roleReqDto.getRoleId());
    }

    public RoleResDto updateRole(RoleReqDto roleReqDto){
        System.out.println("RoleService updateRole ");

        Role role = getRole(roleReqDto.getRoleId());
        System.out.println("RoleService updateRole role toString1 : "+role.toString());
        roleReqMapper.updateFromDto(roleReqDto, role);
        System.out.println("RoleService updateRole role toString2 : "+role.toString());

        return roleResMapper.toDto(role);
    }

    public Role getRole(Long roleId){
        System.out.println("RoleService getRole roleId : "+roleId);
        Role role = roleRepository.getRoleByRoleId(roleId).orElseThrow(()-> new NoSuchElementException("해당되는 Role이 없습니다."));
        System.out.println("RoleService getRole getRoleName : "+role.getRoleName());
        //RoleResDto roleResDto = roleResMapper.toDto(role);
        return role;
    }

    public Role getRole(String roleName){
        System.out.println("RoleService getRole roleName : "+roleName);
        Role role = roleRepository.getRoleByRoleName(roleName).orElseThrow(()-> new NoSuchElementException("해당되는 Role이 없습니다."));
        //RoleResDto roleResDto = roleResMapper.toDto(role);
        return role;
    }

    public RoleResDto getRoleResDto(RoleReqDto roleReqDto){
        System.out.println("RoleService getRoleResDto roleId : "+roleReqDto.getRoleId());
        Role role = roleRepository.getRoleByRoleId(roleReqDto.getRoleId()).orElseThrow(()-> new NoSuchElementException("해당되는 Role이 없습니다."));
        RoleResDto roleResDto = roleResMapper.toDto(role);
        return roleResDto;
    }

    public List<String> getRoleNameList(List<Long> roleIdList){

        return roleIdList.stream().map(r -> {
            return roleRepository.getReferenceById(r).getRoleName();
        }).collect(Collectors.toList());
    }

    public List<RoleResDto> getRoleResDtoList(List<Long> roleIdList){
        //System.out.println("RoleService getRoleResDtoList  : ");
        return roleIdList.stream().map(r -> {
            return roleResMapper.toDto(roleRepository.getReferenceById(r));
        }).collect(Collectors.toList());
    }
}
