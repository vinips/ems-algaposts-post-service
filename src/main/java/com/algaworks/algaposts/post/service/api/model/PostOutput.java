package com.algaworks.algaposts.post.service.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PostOutput {

    private UUID postId;
    private String title;
    private String body;
    private String author;
    private Integer wordCount;
    private Double calculatedValue;

}
