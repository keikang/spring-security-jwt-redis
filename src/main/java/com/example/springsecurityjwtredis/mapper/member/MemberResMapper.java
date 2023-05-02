package com.example.springsecurityjwtredis.mapper.member;

import com.example.springsecurityjwtredis.mapper.GenericMapper;
import com.example.springsecurityjwtredis.mapper.group.GroupResMapper;
import com.example.springsecurityjwtredis.mapper.role.RoleResMapper;
import com.example.springsecurityjwtredis.model.Entity.Authority;
import com.example.springsecurityjwtredis.model.Entity.Member;
import com.example.springsecurityjwtredis.model.reponse.MemberResDto;
import com.example.springsecurityjwtredis.model.reponse.RoleResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberResMapper extends GenericMapper<MemberResDto, Member> {

    RoleResMapper roleResMapper = Mappers.getMapper(RoleResMapper.class);

    GroupResMapper groupResMapper = Mappers.getMapper(GroupResMapper.class);

    default MemberResDto toDto(Member member){
        System.out.println("MemberResMapper toDto ");
        MemberResDto result = MemberResDto.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .email(member.getEmail())
                .build();
/*                .authorityResDtoList(
                        member.getAuthorities().stream().map(a -> {
                            return authorityResMapper.toDto(a);
                        }).collect(Collectors.toList())
                )
                .memberGroupRels(
                        member.getMemberGroupRels().stream().map(a -> {
                            return memberGroupRelResMapper.toDto(a);
                        }).collect(Collectors.toList())
                )*/


        List<RoleResDto> roleResDtoList = new ArrayList<>();
        //System.out.println("MemberResMapper toDto authorityResDtoList size : "+member.getAuthorities().size());
        for (Authority authority : member.getAuthorities()) {
            //System.out.println("MemberResMapper toDto getRole : "+authority.getRole().getRoleId());
            //System.out.println("MemberResMapper toDto getMember : "+authority.getMember().getMemberId());
            roleResDtoList.add(roleResMapper.toDto(authority.getRole()));
        }

        result.setRoleResDtoList(roleResDtoList);
        return result;
    }

}
