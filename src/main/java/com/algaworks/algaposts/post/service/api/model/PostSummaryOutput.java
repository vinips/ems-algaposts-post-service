package com.algaworks.algaposts.post.service.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PostSummaryOutput {

    private UUID postId;
    private String title;
    private String summary;
    private String author;

}
