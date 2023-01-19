package com.newimgur.image.service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.data.spanner.core.SpannerQueryOptions;
import org.springframework.cloud.gcp.data.spanner.core.SpannerTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.Timestamp;
import com.google.cloud.spanner.Statement;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.newimgur.image.model.Image;
import com.newimgur.image.repository.ImagesRepository;
import com.newimgur.image.utils.ImageUtils;

@Service
public class ImageService {
	@Value("${gcs.bucket.name}")
	private String gcsBucketName;
	
	@Autowired
	SpannerTemplate spannerTemplate;
	
	@Autowired
	ImagesRepository imagesRepository;

	public Image getImage(String id) {
		List<Image> images = this.spannerTemplate.query(Image.class, Statement.of("SELECT * FROM Images WHERE id = '" + id + "'"), new SpannerQueryOptions());

		if (images.size() > 0) {
			return images.get(0);
		}
		
		return null;
	}
	
	public Iterable<Image> getLatestImages(int limit, int offset) {
		return imagesRepository.findAll(PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
	}

	public Image createImage(String caption, MultipartFile file) throws IOException {
		UUID id = UUID.randomUUID();
		
		saveFileToGcs(id, file);
		
		Image image = new Image(id.toString(), Timestamp.ofTimeSecondsAndNanos(ZonedDateTime.now().toEpochSecond(), 0), 
				caption, ImageUtils.getFileExtension(file));
		spannerTemplate.insert(image);
		
		return image;
	}
	
	private void saveFileToGcs(UUID id, MultipartFile file) throws IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of(gcsBucketName, id.toString() + ImageUtils.getFileExtension(file));
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, file.getBytes());
	}
}
