package com.example.springsecurityjwtredis.api;


import com.example.springsecurityjwtredis.common.dto.CommonPageLIstResDto;
import com.example.springsecurityjwtredis.model.Entity.Role;
import com.example.springsecurityjwtredis.model.ResponseEntityFactory;
import com.example.springsecurityjwtredis.model.reponse.RoleResDto;
import com.example.springsecurityjwtredis.model.request.RoleReqDto;
import com.example.springsecurityjwtredis.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<RoleResDto> addRole(@RequestBody RoleReqDto roleReqDto){
        System.out.println("RoleController addRole");
        return ResponseEntityFactory.ok(roleService.addRole(roleReqDto));
    }

    @GetMapping("/list")
    public ResponseEntity<CommonPageLIstResDto> roleList(Pageable pageable){
        System.out.println("RoleController roleList");
        return ResponseEntityFactory.ok(roleService.getRolesList(pageable));
    }
/*    @GetMapping("/list")
    public ResponseEntity<RolePageListResDto> roleList(@RequestBody AuthorityReqDto authorityReqDto
            , @PageableDefault(size = 10) Pageable pageable){
        System.out.println("RoleController roleList");
        return ResponseEntityFactory.ok(roleService.getRolesList(authorityReqDto, pageable));
    }*/

    @DeleteMapping("/delete")
    public ResponseEntity<RoleResDto> deleteRole(@RequestBody RoleReqDto roleReqDto){
        System.out.println("RoleController deleteRole");
        Role role = roleService.getRole(roleReqDto.getRoleId());
        roleService.deleteRole(roleReqDto);
        return ResponseEntityFactory.ok(  role.getRoleName() + "이 삭제 되었습니다.");
    }

    @PutMapping("/update")
    public ResponseEntity<RoleResDto> updateRole(@RequestBody RoleReqDto roleReqDto){
        System.out.println("RoleController updateRole");
        return ResponseEntityFactory.ok(roleService.updateRole(roleReqDto));
    }

    @PostMapping("/detail")
    public ResponseEntity<RoleResDto> detailRole(@RequestBody RoleReqDto roleReqDto){
        //public MemberResDto getMember(@PathVariable String memberId){
        System.out.println("RoleController detailRole");
        return ResponseEntityFactory.ok(roleService.getRoleResDto(roleReqDto));
        //return memberService.getMemberRes(memberId);
    }
}
