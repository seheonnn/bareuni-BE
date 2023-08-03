package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Hospital;
import com.umc.BareuniBE.repository.querydsl.HospitalRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalRepositoryCustom {

    @Query(
            value =
                    "SELECT h.hospital_idx as hospitalIdx, h.address, h.hospital_name as hosName, AVG(r.score) as score, COUNT(*) as reviewCnt\n" +
                            "FROM hospital h INNER JOIN review r\n" +
                            "ON h.hospital_idx = r.hospital\n" +
                            "GROUP BY h.hospital_idx\n" +
                            "ORDER BY score DESC",
            nativeQuery = true
    )
    List<Object []> findBestHospital();
}
