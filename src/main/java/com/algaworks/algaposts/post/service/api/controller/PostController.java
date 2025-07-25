package com.algaworks.algaposts.post.service.api.controller;

import com.algaworks.algaposts.post.service.api.model.PostInput;
import com.algaworks.algaposts.post.service.api.model.PostOutput;
import com.algaworks.algaposts.post.service.domain.model.Post;
import com.algaworks.algaposts.post.service.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOutput create(@RequestBody PostInput postInput) {
        Post post = postService.createPost(postInput);


        return PostOutput.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .author(post.getAuthor())
                .build();
    }




}
