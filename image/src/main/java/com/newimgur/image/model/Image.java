package com.newimgur.image.model;

import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;

import com.google.cloud.Timestamp;

@Table(name="Images")
public class Image {
	@PrimaryKey
    private String id;
    private Timestamp createdAt;
    private String caption;

    public Image(String id, Timestamp createdAt, String caption) {
        this.id = id;
        this.createdAt = createdAt;
        this.caption = caption;
    }

    public String getId() {
        return this.id;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public String getCaption() {
        return this.caption;
    }
}

