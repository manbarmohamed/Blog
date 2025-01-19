package com.tech.blog.repository;

import com.tech.blog.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {}
