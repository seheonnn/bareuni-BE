package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.entities.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query(
            value =
                    "select c.*, COUNT(le.community) as likeCnt\n" +
                            "from community c\n" +
                            "left outer join like_entity le\n" +
                            "on c.community_idx = le.community\n" +
                            "group by c.community_idx",
            nativeQuery = true
    )
    List<CommunityRes.CommunityListRes> findAllCommunity_Pagination(Pageable pageable);


}
