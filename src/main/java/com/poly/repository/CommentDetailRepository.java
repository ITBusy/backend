package com.poly.repository;

import com.poly.entity.CommentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDetailRepository extends JpaRepository<CommentDetails, Long> {
    List<CommentDetails> findByComment_CommentId(Long commentId);

}
