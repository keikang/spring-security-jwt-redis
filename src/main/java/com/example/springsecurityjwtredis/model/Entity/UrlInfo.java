package com.example.springsecurityjwtredis.model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
@Table(name = "tb_url_info", schema = "public")
public class UrlInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long urlid;

    private String urlAddr;

    private String urlRole;
}
