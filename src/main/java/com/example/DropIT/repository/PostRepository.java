package com.example.DropIT.repository;

import com.example.DropIT.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post,Long> {
    boolean existsPostById(Long id);
}
