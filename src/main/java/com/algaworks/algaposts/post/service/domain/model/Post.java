package com.algaworks.algaposts.post.service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private UUID id;

    @Column(length = 1000)
    private String body;
    private String title;
    private String author;
    private Integer wordCount;
    private Double calculatedValue;
}
