package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalRepositoryCustom {

    @Query(
            value =
                    "SELECT h.hospital_idx as hospitalIdx, h.address, h.hospital_name as hosName, AVG(r.total_score) as score, COUNT(*) as reviewCnt, h.image\n" +
                            "FROM hospital h INNER JOIN review r\n" +
                            "ON h.hospital_idx = r.hospital\n" +
                            "GROUP BY h.hospital_idx\n" +
                            "ORDER BY score DESC\n" +
                            "LIMIT 3",
            nativeQuery = true
    )
    List<Object []> findBestHospital();

    @Query(
            value =
                    "SELECT\n" +
                            "    h.hospital_idx,\n" +
                            "    h.hospital_name,\n" +
                            "    h.telephone,\n" +
                            "    h.keywords,\n" +
                            "    h.open_time,\n" +
                            "    h.closed_day,\n" +
                            "    h.lunch_time,\n" +
                            "    h.content,\n" +
                            "    h.address,\n" +
                            "    h.images,\n" +
                            "    h.bookable,\n" +
                            "    (CASE\n" +
                            "        WHEN IFNULL(treatment_stats.bad, 0) >= IFNULL(treatment_stats.normal, 0) AND IFNULL(treatment_stats.bad, 0) >= IFNULL(treatment_stats.good, 0)\n" +
                            "        THEN 'BAD'\n" +
                            "        WHEN IFNULL(treatment_stats.normal, 0) >= IFNULL(treatment_stats.bad, 0) AND IFNULL(treatment_stats.normal, 0) >= IFNULL(treatment_stats.good, 0)\n" +
                            "        THEN 'NORMAL'\n" +
                            "        ELSE 'GOOD'\n" +
                            "     END) AS treatment,\n" +
                            "    ROUND(\n" +
                            "        (GREATEST(IFNULL(treatment_stats.bad, 0), IFNULL(treatment_stats.normal, 0), IFNULL(treatment_stats.good, 0)) / NULLIF(treatment_stats.total_reviews, 0)) * 100,\n" +
                            "        2\n" +
                            "    ) AS treatment_ratio,\n" +
                            "    (CASE\n" +
                            "        WHEN IFNULL(service_stats.bad, 0) >= IFNULL(service_stats.normal, 0) AND IFNULL(service_stats.bad, 0) >= IFNULL(service_stats.good, 0)\n" +
                            "        THEN 'BAD'\n" +
                            "        WHEN IFNULL(service_stats.normal, 0) >= IFNULL(service_stats.bad, 0) AND IFNULL(service_stats.normal, 0) >= IFNULL(service_stats.good, 0)\n" +
                            "        THEN 'NORMAL'\n" +
                            "        ELSE 'GOOD'\n" +
                            "     END) AS service,\n" +
                            "    ROUND(\n" +
                            "        (GREATEST(IFNULL(service_stats.bad, 0), IFNULL(service_stats.normal, 0), IFNULL(service_stats.good, 0)) / NULLIF(service_stats.total_reviews, 0)) * 100,\n" +
                            "        2\n" +
                            "    ) AS service_ratio,\n" +
                            "    (CASE\n" +
                            "        WHEN IFNULL(equipment_stats.bad, 0) >= IFNULL(equipment_stats.normal, 0) AND IFNULL(equipment_stats.bad, 0) >= IFNULL(equipment_stats.good, 0)\n" +
                            "        THEN 'BAD'\n" +
                            "        WHEN IFNULL(equipment_stats.normal, 0) >= IFNULL(equipment_stats.bad, 0) AND IFNULL(equipment_stats.normal, 0) >= IFNULL(equipment_stats.good, 0)\n" +
                            "        THEN 'NORMAL'\n" +
                            "        ELSE 'GOOD'\n" +
                            "     END) AS equipment,\n" +
                            "    ROUND(\n" +
                            "        (GREATEST(IFNULL(equipment_stats.bad, 0), IFNULL(equipment_stats.normal, 0), IFNULL(equipment_stats.good, 0)) / NULLIF(equipment_stats.total_reviews, 0)) * 100,\n" +
                            "        2\n" +
                            "    ) AS equipment_ratio,\n" +
                            "    ROUND(AVG(r.total_score), 2) AS total_score,\n" +
                            "    COUNT(*) AS review_cnt\n" +
                            "FROM hospital h\n" +
                            "LEFT JOIN (\n" +
                            "    SELECT hospital,\n" +
                            "        SUM(CASE WHEN treatment_score = 'BAD' THEN 1 ELSE 0 END) AS bad,\n" +
                            "        SUM(CASE WHEN treatment_score = 'NORMAL' THEN 1 ELSE 0 END) AS normal,\n" +
                            "        SUM(CASE WHEN treatment_score = 'GOOD' THEN 1 ELSE 0 END) AS good,\n" +
                            "        COUNT(*) AS total_reviews\n" +
                            "    FROM review\n" +
                            "    GROUP BY hospital\n" +
                            ") AS treatment_stats ON h.hospital_idx = treatment_stats.hospital\n" +
                            "LEFT JOIN (\n" +
                            "    SELECT hospital,\n" +
                            "        SUM(CASE WHEN service_score = 'BAD' THEN 1 ELSE 0 END) AS bad,\n" +
                            "        SUM(CASE WHEN service_score = 'NORMAL' THEN 1 ELSE 0 END) AS normal,\n" +
                            "        SUM(CASE WHEN service_score = 'GOOD' THEN 1 ELSE 0 END) AS good,\n" +
                            "        COUNT(*) AS total_reviews\n" +
                            "    FROM review\n" +
                            "    GROUP BY hospital\n" +
                            ") AS service_stats ON h.hospital_idx = service_stats.hospital\n" +
                            "LEFT JOIN (\n" +
                            "    SELECT hospital,\n" +
                            "        SUM(CASE WHEN equipment_score = 'BAD' THEN 1 ELSE 0 END) AS bad,\n" +
                            "        SUM(CASE WHEN equipment_score = 'NORMAL' THEN 1 ELSE 0 END) AS normal,\n" +
                            "        SUM(CASE WHEN equipment_score = 'GOOD' THEN 1 ELSE 0 END) AS good,\n" +
                            "        COUNT(*) AS total_reviews\n" +
                            "    FROM review\n" +
                            "    GROUP BY hospital\n" +
                            ") AS equipment_stats ON h.hospital_idx = equipment_stats.hospital\n" +
                            "LEFT JOIN review r ON h.hospital_idx = r.hospital\n" +
                            "WHERE h.hospital_idx = :hospitalIdx\n" +
                            "GROUP BY h.hospital_idx",
            nativeQuery = true
    )
    HospitalRes.HospitalDetailResult findHospitalDetailById(@Param("hospitalIdx") Long hospitalIdx);
}
