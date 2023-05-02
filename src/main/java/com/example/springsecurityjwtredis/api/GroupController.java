package com.example.springsecurityjwtredis.api;


import com.example.springsecurityjwtredis.model.Entity.Group;
import com.example.springsecurityjwtredis.model.ResponseEntityFactory;
import com.example.springsecurityjwtredis.model.reponse.GroupResDto;
import com.example.springsecurityjwtredis.model.reponse.RolePageListResDto;
import com.example.springsecurityjwtredis.model.request.GroupReqDto;
import com.example.springsecurityjwtredis.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<GroupResDto> addGroup(@RequestBody GroupReqDto groupReqDto){
        System.out.println("GroupController addRole");
        return ResponseEntityFactory.ok(groupService.addGroup(groupReqDto));
    }

    @PutMapping("/update")
    public ResponseEntity<GroupResDto> updateGroup(@RequestBody GroupReqDto groupReqDto){
        System.out.println("GroupController updateGroup");
        return ResponseEntityFactory.ok(groupService.updateGroup(groupReqDto));
    }

    @GetMapping("/list")
    public ResponseEntity<RolePageListResDto> roleList(Pageable pageable){
        System.out.println("GroupController roleList");
        return ResponseEntityFactory.ok(groupService.getGroupList(pageable));
    }

    @PostMapping("/detail")
    public ResponseEntity<GroupResDto> detailGroup(@RequestBody GroupReqDto groupReqDto){
        //public MemberResDto getMember(@PathVariable String memberId){
        System.out.println("GroupController detailGroup");
        return ResponseEntityFactory.ok(groupService.getGroupResDto(groupReqDto));
        //return memberService.getMemberRes(memberId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRole(@RequestBody GroupReqDto groupReqDto){
        System.out.println("GroupController deleteRole");
        Group group = groupService.getGroup(groupReqDto.getGroupId());
        groupService.deletGroup(groupReqDto);
        return ResponseEntityFactory.ok(  group.getGroupName() + "이 삭제 되었습니다.");
    }
}
