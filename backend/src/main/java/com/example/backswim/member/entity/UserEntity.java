package com.example.backswim.member.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seq;

    @NotNull
    @Column(unique = true)
    private String userEmail;

    @NotNull
    private String password;

    @NotNull
    private LocalDateTime regdate;

    private String emailAuthKey;

    private boolean emailAuthYn;

    private LocalDateTime emailAuthDate;

    private String resetPasswordKey;

    private LocalDateTime resetPasswordLimitDt;

    private String imgUrl;
}
