package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Scrap;
import com.umc.BareuniBE.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query(
            value =
                    "SELECT s.scrapIdx, s.createdAt, s.updatedAt, s.user.userIdx, s.hospital " +
                            "FROM Scrap s " +
                            "WHERE s.user = :user "
    )
    List<Object[]> MyScrapList(@Param("user") User user, Pageable pageable);
}
