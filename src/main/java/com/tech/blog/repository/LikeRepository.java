package com.tech.blog.repository;

import com.tech.blog.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    Optional<Like> findByUserIdAndPostId(
            @Param("userId") Long userId,
            @Param("postId") Long postId);

    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    boolean existsByUserIdAndPostId(
            @Param("userId") Long userId,
            @Param("postId") Long postId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    long countByPostId(@Param("postId") Long postId);
}