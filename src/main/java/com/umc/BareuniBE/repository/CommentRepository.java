package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Comment;
import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCommunity(Community community);

    void deleteAllByUser(User user);
}
