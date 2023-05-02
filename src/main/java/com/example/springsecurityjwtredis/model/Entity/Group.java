package com.example.springsecurityjwtredis.model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tb_group", schema = "public")
@ToString
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    private String groupName;

    private String groupDescription;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.ALL})
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<MemberGroupRel> memberGroupRels = new HashSet<>();

    public void addAuthority(Group group, Role role){
        //System.out.println("Member entity addAuthority roleName : "+role.getRoleName());
        //System.out.println("Member entity addAuthority roleId : "+role.getRoleId());
        authorities.add(Authority.of(group, role));
        //roles.forEach(v ->  authorities.add(Authority.of(member, roles)));
        //authorities.add(authority);
    }

    public List<Long> getRoleIds(){
        return authorities.stream()
                .map(Authority::getRole)
                .map(Role::getRoleId)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Long> getGroupIds(){
        return memberGroupRels.stream()
                .map(MemberGroupRel::getGroup)
                .map(Group::getGroupId)
                .collect(Collectors.toUnmodifiableList());
    }
}
