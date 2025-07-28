package com.algaworks.algaposts.post.service.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostInput {

    @NotBlank
    private String title;

    @NotNull
    private String body;

    @NotNull
    private String author;

}
