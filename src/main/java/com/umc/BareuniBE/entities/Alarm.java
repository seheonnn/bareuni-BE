package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import com.umc.BareuniBE.global.enums.AlarmType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="alarmIdx")
    private Long alarmIdx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "community")
    private Community community;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "alarmType", nullable = false)
    private AlarmType alarmType;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "url", nullable = false)
    private String url;
}
