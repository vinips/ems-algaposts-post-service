package com.algaworks.algaposts.post.service.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PostProcessingData {

    private UUID postId;
    private String postBody;
}
