// PostRepository.java
package com.tech.blog.repository;

import com.tech.blog.model.entity.Post;
import com.tech.blog.model.dto.response.PostSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.category LEFT JOIN FETCH p.tags WHERE p.id = :id")
    Optional<Post> findByIdWithAssociations(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.id = :id")
    void incrementViews(@Param("id") Long id);

    @Query("SELECT new com.tech.blog.model.dto.response.PostSummaryResponse(" +
            "p.id, p.title, p.createdAt, p.views, " +
            "(SELECT COUNT(c) FROM Comment c WHERE c.post = p), " +
            "(SELECT COUNT(l) FROM Like l WHERE l.post = p)) " +
            "FROM Post p")
    Page<PostSummaryResponse> findAllPostSummaries(Pageable pageable);
}