package com.example.springsecurityjwtredis.model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
@Table(name = "tb_menu", schema = "public")
public class Menu {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String menuId;

    private String menuUrl;

    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id")
    private Menu parentMenu;

    private Long menuOrder;

    private Long roleId;

    @OneToMany(mappedBy = "parent")
    private List<Menu> childMenuList = new ArrayList<>();

}
