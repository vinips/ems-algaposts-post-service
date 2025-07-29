package com.algaworks.algaposts.post.service.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostInput {

    @NotBlank
    @Size(min= 1, max= 50)
    private String title;

    @NotBlank
    @Size(min= 1, max= 1000)
    private String body;

    @NotBlank
    @Size(min= 1, max= 50)
    private String author;

}
