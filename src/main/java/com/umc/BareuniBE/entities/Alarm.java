package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
//@Getter
//@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="alarmIdx")
    private Long alarmIdx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "community")
    private Community community;

    @Column(name = "alarmType", nullable = false)
    private String alarmType;

    @Column(name = "content", nullable = false)
    private String content;
}
