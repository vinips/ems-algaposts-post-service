package com.algaworks.algaposts.post.service.api.controller;

import com.algaworks.algaposts.post.service.api.model.PostInput;
import com.algaworks.algaposts.post.service.api.model.PostOutput;
import com.algaworks.algaposts.post.service.domain.model.Post;
import com.algaworks.algaposts.post.service.domain.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostOutput> create(@RequestBody @Valid PostInput postInput) {
        Post post = postService.createPost(postInput);

        return ResponseEntity
                .created(getCreationURI(post.getId()))
                .body(convertToOutputModel(post));
    }

    private PostOutput convertToOutputModel(Post post) {
        return PostOutput.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .author(post.getAuthor())
                .wordCount(post.getWordCount())
                .calculatedValue(post.getCalculatedValue())
                .build();
    }

    private URI getCreationURI(UUID postId) {
        return UriComponentsBuilder
                .fromPath("/api/posts/{postID}")
                .buildAndExpand(postId)
                .toUri();
    }


}
