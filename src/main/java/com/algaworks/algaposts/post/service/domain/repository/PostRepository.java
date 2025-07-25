package com.algaworks.algaposts.post.service.domain.repository;

import com.algaworks.algaposts.post.service.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
