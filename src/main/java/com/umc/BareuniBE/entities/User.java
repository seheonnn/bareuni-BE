package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import com.umc.BareuniBE.global.enums.GenderType;
import com.umc.BareuniBE.global.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userIdx")
    private Long userIdx;

    @Column(name = "email", nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    @Size(max = 9)
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    @Pattern(regexp = "/^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/", message = "전화번호 형식이 맞지 않습니다.")
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderType gender;

    @Column(name = "age", nullable = false)
    private Long age;

    // 치아 교정 여부
    @Column(name = "ortho", nullable = false)
    private boolean ortho;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role;

    @Column(name = "provider")
    private String provider;
}
