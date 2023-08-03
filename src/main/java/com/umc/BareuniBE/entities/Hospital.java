package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hospital extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="hospitalIdx")
    private Long hospitalIdx;

    @Column(name = "hospitalName", nullable = false)
    private String hospitalName;

    @Column(name = "telephone", nullable = false)
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식에 맞지 않습니다.")
    private String telephone;

    @Column(name = "keywords", nullable = false)
    private String keywords;

    @Column(name = "openTime", nullable = false)
    private String openTime;

    @Column(name = "closedDay", nullable = false)
    private String closedDay;

    @Column(name = "lunchTime", nullable = false)
    private String lunchTime;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "bookable", nullable = false)
    private boolean bookable;

    @Column(name = "summary", nullable = false)
    private String summary;

}
