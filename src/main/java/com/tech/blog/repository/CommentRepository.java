package com.tech.blog.repository;

import com.tech.blog.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.post p LEFT JOIN FETCH p.comments WHERE c.id = :commentId")
    Optional<Comment> findByIdWithPostAndComments(@Param("commentId") Long commentId);

    List<Comment> findByPostId(Long postId);
}