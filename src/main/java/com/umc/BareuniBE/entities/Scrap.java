package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
//@Getter
//@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Scrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="scrapIdx")
    private Long scrapIdx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital")
    private Hospital hospital;

    public Scrap(User user, Hospital hospital) {
        this.user = user;
        this.hospital = hospital;
    }
}
