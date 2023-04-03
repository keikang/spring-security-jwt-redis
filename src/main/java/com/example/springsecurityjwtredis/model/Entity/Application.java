package com.example.springsecurityjwtredis.model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "tb_application", schema = "public")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long applicationId;

    private String applicationName;

    private String applicationVersion;

    private String lincense;


}
