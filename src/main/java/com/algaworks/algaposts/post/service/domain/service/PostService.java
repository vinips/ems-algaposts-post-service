package com.algaworks.algaposts.post.service.domain.service;

import com.algaworks.algaposts.post.service.api.model.PostInput;
import com.algaworks.algaposts.post.service.api.model.PostOutput;
import com.algaworks.algaposts.post.service.domain.model.Post;
import com.algaworks.algaposts.post.service.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(PostInput postInput) {
        return Post.builder()
                .author(postInput.getAuthor())
                .build();
    }
}
