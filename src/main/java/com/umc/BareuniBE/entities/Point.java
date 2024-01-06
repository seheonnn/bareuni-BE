//package com.umc.BareuniBE.entities;
//
//import com.umc.BareuniBE.global.BaseEntity;
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Point extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="pointIdx")
//    private Long pointIdx;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "user")
//    private User user;
//
//    @Column(name = "content", nullable = false)
//    private String content;
//
//    @Column(name = "amount", nullable = false)
//    private Long amount;
//
//    @Column(name = "expiryDate", nullable = false)
//    private LocalDateTime expiryDate;
//}
