package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.LikeEntity;
import com.umc.BareuniBE.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndCommunity(User user, Community community);
}
