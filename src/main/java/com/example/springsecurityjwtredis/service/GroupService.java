package com.example.springsecurityjwtredis.service;

import com.example.springsecurityjwtredis.common.dto.CommonPageLIstResDto;
import com.example.springsecurityjwtredis.mapper.group.GroupReqMapper;
import com.example.springsecurityjwtredis.mapper.group.GroupResMapper;
import com.example.springsecurityjwtredis.model.Entity.Group;
import com.example.springsecurityjwtredis.model.Entity.MemberGroupRel;
import com.example.springsecurityjwtredis.model.reponse.GroupResDto;
import com.example.springsecurityjwtredis.model.request.GroupReqDto;
import com.example.springsecurityjwtredis.repository.AuthorityRepository;
import com.example.springsecurityjwtredis.repository.GroupRepository;
import com.example.springsecurityjwtredis.repository.MemberGroupRelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    private final MemberGroupRelRepository memberGroupRelRepository;

    private final AuthorityRepository authorityRepository;

    private final GroupReqMapper groupReqMapper;

    private final GroupResMapper groupResMapper;

    private final RoleService roleService;

    public GroupResDto addGroup(GroupReqDto groupReqDto){
        Group group = groupReqMapper.toEntity(groupReqDto);
        groupReqDto.getRoleIdList().forEach(r->group.addAuthority(group, roleService.getRole(r)));
        Group result = groupRepository.save(group);
        GroupResDto groupResDto = groupResMapper.toDto(result);
        groupResDto.setRoleResDtoList(roleService.getRoleResDtoList(result.getRoleIds()));
        return groupResDto;
    }

    public GroupResDto updateGroup(GroupReqDto groupReqDto){
        //System.out.println("GroupService updateGroup GroupId : "+groupReqDto.getGroupId());
        authorityRepository.deleteByGroupGroupId(groupReqDto.getGroupId());
        authorityRepository.flush();
        //Member member = Member.ofUser(memberReqDto);
        Group group = groupRepository.findByGroupId(groupReqDto.getGroupId()).orElseThrow(() -> new NoSuchElementException("해당하는 Group이 없습니다."));
        groupReqMapper.updateFromDto(groupReqDto, group);
        groupReqDto.getRoleIdList().forEach(r->group.addAuthority(group, roleService.getRole(r)));
        //member.addAuthority(member, memberReqDto.getRoles());
        Group result = groupRepository.save(group);

        GroupResDto groupResDto = groupResMapper.toDto(result);
        groupResDto.setRoleResDtoList(roleService.getRoleResDtoList(result.getRoleIds()));
        return groupResDto;
    }

    public Group getGroup(Long groupId){
        //System.out.println("GroupService getGroup groupId : "+groupId);
        Group group = groupRepository.findByGroupId(groupId).orElseThrow(()-> new NoSuchElementException("해당되는 Group이 없습니다."));
        return group;
    }

    public CommonPageLIstResDto getGroupList(Pageable pageable){
        System.out.println("GroupService getGroupList pageable sort : "+pageable.getSort().toString());
        //TODO 현재는 front 솔루션에서 페이징 구현이 되있으므로 사용하지 않음, 향후 성능이슈 발생시 대체
        //List<RoleResConverter> pageList = roleRepository.rolePageList(pageable);
        List<Group> totalList = groupRepository.findAll();
        List<GroupResDto> groupResDtoList = new ArrayList<>();
        for (Group group : totalList) {
            GroupResDto groupResDto = groupResMapper.toDto(group);
            groupResDto.setRoleResDtoList(roleService.getRoleResDtoList(group.getRoleIds()));
            groupResDtoList.add(groupResDto);
        }

        int currentPage = pageable.getPageNumber();
        int totalPage = (totalList.size() / pageable.getPageSize());

        int pageBlock = 2;
        int startBlockPage = ((currentPage) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock + 1;
        endBlockPage = totalPage < endBlockPage ? totalPage : endBlockPage;

        return CommonPageLIstResDto.builder()
                .pageList(groupResDtoList)
                .startBlockPage(startBlockPage)
                .endBlockPage(endBlockPage)
                .build();
    }

    public void deletGroup(GroupReqDto groupReqDto){
        System.out.println("GroupService deletGroup");
        authorityRepository.deleteByGroupGroupId(groupReqDto.getGroupId());
        authorityRepository.flush();
        memberGroupRelRepository.deleteByGroupGroupId(groupReqDto.getGroupId());
        memberGroupRelRepository.flush();
        groupRepository.deleteById(groupReqDto.getGroupId());
    }

    public GroupResDto getGroupResDto(GroupReqDto groupReqDto){
        System.out.println("GroupService getGroupResDto getGroupId : "+groupReqDto.getGroupId());
        Group group = groupRepository.findByGroupId(groupReqDto.getGroupId()).orElseThrow(()-> new NoSuchElementException("해당되는 Group이 없습니다."));
        GroupResDto groupResDto = groupResMapper.toDto(group);
        groupResDto.setRoleResDtoList(roleService.getRoleResDtoList(group.getRoleIds()));
        return groupResDto;
    }

    public List<String> getGroupNameList(List<Long> groupIdList){

        return groupIdList.stream().map(r -> {
            return groupRepository.getReferenceById(r).getGroupName();
        }).collect(Collectors.toList());
    }

    public List<GroupResDto> getGroupResList(List<Long> groupIdList){
        //System.out.println("GroupService getGroup getGroupResList : ");
        List<GroupResDto> groupResDtoList = new ArrayList<>();

        for (Long groupId : groupIdList) {
            Group group = getGroup(groupId);
            GroupResDto groupResDto = groupResMapper.toDto(group);

            groupResDto.setRoleResDtoList(roleService.getRoleResDtoList(group.getRoleIds()));
            groupResDtoList.add(groupResDto);
        }

        return groupResDtoList;
    }

    public List<String> getGroupNameListByMemberId(String memberId){

        List<String> groupRoleNameList = memberGroupRelRepository.findByMember_MemberId(memberId).stream()
                .map(MemberGroupRel::getGroup)
                .collect(Collectors.toList()).stream().flatMap(group -> {
                    return roleService.getRoleNameList(group.getRoleIds()).stream();
                }).collect(Collectors.toList());;

/*        for (Group group : getGroupListByMemberId) {
            groupRoleNameList.addAll(roleService.getRoleNameList(group.getRoleIds()));
        }*/
/*        List<String> groupRoleNameList = getGroupListByMemberId.stream().flatMap(group -> {
            return roleService.getRoleNameList(group.getRoleIds()).stream();
        }).collect(Collectors.toList());*/

        return groupRoleNameList;
    }
}
