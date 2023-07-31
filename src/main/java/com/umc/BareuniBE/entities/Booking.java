package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
//@Getter
//@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bookingIdx")
    private Long bookingIdx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital")
    private Hospital hospital;

    // 예약 방식
    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "bookingDate")
    private LocalDateTime bookingDate;
}
