package com.tech.blog.repository;

import com.tech.blog.model.entity.Post;
import com.tech.blog.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {


    boolean existsByName(String s);
}
