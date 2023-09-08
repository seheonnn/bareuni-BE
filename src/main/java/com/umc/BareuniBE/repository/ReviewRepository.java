package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Review;
import com.umc.BareuniBE.global.enums.GenderType;
import com.umc.BareuniBE.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom, JpaSpecificationExecutor<Review> {
    List<Review> findReviewByUser(Pageable pageable, User user);

    List<Review> findByHospital_HospitalIdx(Pageable pageable, Long hospitalIdx);
    List<Review> findByReceiptAndUserGender(boolean receipt, GenderType gender, Pageable pageable);

    List<Review> findByHospital_HospitalIdxAndReceiptAndUser_GenderOrUser_GenderIsNull(Long hospitalIdx,boolean receipt, GenderType gender, Pageable pageable);

    void deleteAllByUser(User user);
}
