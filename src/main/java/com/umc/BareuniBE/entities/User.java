package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import com.umc.BareuniBE.global.enums.GenderType;
import com.umc.BareuniBE.global.enums.RoleType;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userIdx")
    private Long userIdx;

    @Column(name = "email", nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9]).{8,64}$", message = "비밀번호가 형식에 맞지 않습니다.")
    private String password;

    @Column(name = "nickname", nullable = false)
    @Size(max = 9)
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$", message = "전화번호 형식이 맞지 않습니다.")
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

    @Column(name = "profile")
    @ColumnDefault("'기본 이미지'")
    private String profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<Booking>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Community> communities = new ArrayList<Community>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LikeEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<Review>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

    // UserDetails 상속
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
