package com.newimgur.image.utils;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

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
}
