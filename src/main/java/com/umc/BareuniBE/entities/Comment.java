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
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="commentIdx")
    private Long commentIdx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "community")
    private Community community;

    @Column(name = "comment", nullable = false)
    private String comment;
}
