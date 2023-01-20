package com.newimgur.image.model;

import java.time.LocalDateTime;

public class ImageDto {
	private String id;
	private String url;
	private LocalDateTime createdAt;
	private String caption;
	
	public ImageDto(String id, String url, LocalDateTime createdAt, String caption) {
		this.id = id;
		this.url = url;
		this.createdAt = createdAt;
		this.caption = caption;
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCaption() {
		return caption;
	}
}
