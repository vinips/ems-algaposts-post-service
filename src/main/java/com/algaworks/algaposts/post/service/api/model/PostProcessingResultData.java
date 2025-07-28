package com.algaworks.algaposts.post.service.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PostProcessingResultData {

    private UUID postId;
    private Integer wordCount;
    private Double calculatedValue;
}
