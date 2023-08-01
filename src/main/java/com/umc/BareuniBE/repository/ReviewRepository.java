package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Review;
import com.umc.BareuniBE.global.enums.GenderType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {




    List<Review> findByReceiptAndUserGender(boolean receipt, GenderType gender, Pageable pageable);


}
