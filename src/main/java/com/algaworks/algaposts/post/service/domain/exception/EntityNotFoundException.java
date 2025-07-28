package com.algaworks.algaposts.post.service.domain.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(UUID id) {
        this(String.format("Entity %s Not Found", id));
    }





}
