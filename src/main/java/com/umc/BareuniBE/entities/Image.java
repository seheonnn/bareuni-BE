package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="imageIdx")
    private Long imageIdx;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "review")
    private Review review;

    @Column(name = "imageName", nullable = false)
    private String imageName;

    @Column(name = "url", nullable = false)
    private String url;
}
