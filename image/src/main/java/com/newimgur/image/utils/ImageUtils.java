package com.newimgur.image.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.newimgur.image.model.Image;
import com.newimgur.image.model.ImageDto;

public class ImageUtils {
	public static String getFileExtension(MultipartFile file) {
		switch (file.getContentType()) {
			case MediaType.IMAGE_JPEG_VALUE:
				return ".jpg";
			case MediaType.IMAGE_GIF_VALUE:
				return ".gif";
			case MediaType.IMAGE_PNG_VALUE:
				return ".png";
		}
		
		return null;
	}
	
	public static ImageDto toImageDto(Image image) {
		return new ImageDto(image.getId(), 
				image.getId() + image.getFileType(),
				LocalDateTime.ofEpochSecond(image.getCreatedAt().getSeconds(), 0, ZoneOffset.UTC),
				image.getCaption());
	}
}
