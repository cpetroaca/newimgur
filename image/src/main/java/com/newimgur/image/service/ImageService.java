package com.newimgur.image.service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.Timestamp;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.newimgur.image.model.Image;
import com.newimgur.image.repository.ImagesRepository;

@Service
public class ImageService {
	@Value("${gcs.bucket.name}")
	private String gcsBucketName;
	
	@Autowired
	ImagesRepository imagesRepository;

	public Image getImage(String id) {
		Optional<Image> opt = imagesRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		
		return null;
	}

	public Image createImage(String caption, MultipartFile file) throws IOException {
		UUID id = UUID.randomUUID();
		
		saveFileToGcs(id, file);
		
		Image image = new Image(id.toString(), Timestamp.ofTimeSecondsAndNanos(ZonedDateTime.now().toEpochSecond(), 0), caption);
		imagesRepository.save(image);
		
		return image;
	}
	
	private void saveFileToGcs(UUID id, MultipartFile file) throws IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of(gcsBucketName, id.toString() + getFileExtension(file));
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, file.getBytes());
	}
	
	private String getFileExtension(MultipartFile file) {
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
