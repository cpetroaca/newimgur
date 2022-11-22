package com.newimgur.image.model;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Image {
    private UUID id;
    private ZonedDateTime createdAt;
    private String caption;

    public Image(UUID id, ZonedDateTime createdAt, String caption) {
        this.id = id;
        this.createdAt = createdAt;
        this.caption = caption;
    }

    public UUID getId() {
        return this.id;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public String getCaption() {
        return this.caption;
    }
}

