package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Review;
import com.umc.BareuniBE.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(
            value =
                    "SELECT r.reviewIdx, r.createdAt, r.updatedAt, r.user.userIdx, r.content, r.equipmentScore, r.receipt, r.serviceScore, r.payment, r.totalScore, r.treatmentScore, r.hospital " +
                            "FROM Review r " +
                            "WHERE r.user = :user " +
                            "ORDER BY r.createdAt DESC"
    )
    List<Object[]> MyReviewList(@Param("user") User user, Pageable pageable);
}
