package com.newimgur.image.model;

import org.springframework.cloud.gcp.data.spanner.core.mapping.Column;
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;

import com.google.cloud.Timestamp;

@Table(name="Images")
public class Image {
	@PrimaryKey(keyOrder = 1)
	@Column(name = "id")
    private String id;
	@PrimaryKey(keyOrder = 2)
	@Column(name = "createdAt")
    private Timestamp createdAt;
    private String caption;
    private String fileType;
    
    public Image(String id, Timestamp createdAt, String caption, String fileType) {
        this.id = id;
        this.createdAt = createdAt;
        this.caption = caption;
        this.fileType = fileType;
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
    
    public String getFileType() {
    	return this.fileType;
    }
}

