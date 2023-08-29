package com.umc.BareuniBE.entities;

import com.umc.BareuniBE.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="communityIdx")
    private Long communityIdx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<LikeEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();
}
