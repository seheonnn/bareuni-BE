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

    void deleteAllByUser(User user);

    @Query(
            value =  "select t.hospital_idx, t.hospital_name, t.summary, t.address, t.images, t.reviewAvg  from (select h.*, AVG(r.total_score) as reviewAvg from hospital h left outer join review r on h.hospital_idx = r.hospital group by h.hospital_idx) t left outer join scrap s on t.hospital_idx = s.hospital where s.user = :user_idx ;",
            nativeQuery = true
    )
    List<Object[]> findAllMyScrapHosList(@Param("user_idx") Long user_idx);
}
