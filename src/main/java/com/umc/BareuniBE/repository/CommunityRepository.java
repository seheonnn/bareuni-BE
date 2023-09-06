package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Community;

import com.umc.BareuniBE.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {

//    @Query(
//            value =
//                    "select c.*, COUNT(le.community) as likeCnt\n" +
//                            "from community c\n" +
//                            "left outer join like_entity le\n" +
//                            "on c.community_idx = le.community\n" +
//                            "group by c.community_idx",
//            nativeQuery = true
//    )
//    List<Object []> findAllCommunity_Pagination(Pageable pageable);


    @Query(
            value =
                    "select c.*, COUNT(le.community) as likeCnt " +
                            "from community c " +
                            "left outer join like_entity le " +
                            "on c.community_idx = le.community " +
                            "group by c.community_idx " +
                            "order by case " +
                            "when :val = 'created' then c.created_at " +
                            "when :val = 'like' then likeCnt " +
                            "else c.created_at " +
                            "end desc ; ",
            nativeQuery = true
    )
    List<Object []> findAllCommunity_custom(@Param("val") String val);

    @Query(
            value =
                    "SELECT c.community_idx, c.created_at, c.updated_at, c.content, u.user_idx, COUNT(l.like_idx) AS likeCnt " +
                            "FROM community c " +
                            "LEFT OUTER JOIN like_entity l " +
                            "ON c.community_idx = l.community " +
                            "LEFT OUTER JOIN user u " +
                            "ON c.user = u.user_idx " +
                            "WHERE c.user = :user " +
                            "GROUP BY c.community_idx " +
                            "ORDER BY c.created_at DESC",
            countQuery =
                    "SELECT COUNT(c.community_idx) " +
                            "FROM community c " +
                            "LEFT OUTER JOIN like_entity l " +
                            "ON c.community_idx = l.community " +
                            "LEFT OUTER JOIN user u " +
                            "ON c.user = u.user_idx " +
                            "WHERE c.user = :user",
            nativeQuery = true
    )
    List<Object[]> MyCommunityList(@Param("user") User user, Pageable pageable);

    @Query(
            value =
                    "select c.*, COUNT(le.community) as likeCnt\n" +
                            "    from (SELECT * FROM community where DATE(created_at) >= DATE_SUB(NOW(), INTERVAL 7 DAY)) c\n" +
                            "    left outer join like_entity le\n" +
                            "    on c.community_idx = le.community\n" +
                            "    group by c.community_idx\n" +
                            "    order by likeCnt DESC\n" +
                            "    limit 4",
            nativeQuery = true
    )
    List<Object[]> getBestCommunityList();

    void deleteAllByUser(User user);
}

