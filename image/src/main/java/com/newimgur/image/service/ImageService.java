package com.newimgur.image.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

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
import com.newimgur.image.model.ImageDto;
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

	public ImageDto getImage(String id) {
		List<Image> images = this.spannerTemplate.query(Image.class, Statement.of("SELECT * FROM Images WHERE id = '" + id + "'"), new SpannerQueryOptions());

		if (images.size() > 0) {
			return ImageUtils.toImageDto(images.get(0));
		}
		
		return null;
	}
	
	public List<ImageDto> getLatestImages(int limit, int offset) {
		Iterable<Image> it = imagesRepository.findAll(PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
		List<ImageDto> imageDtoList = new ArrayList<>();
		StreamSupport.stream(it.spliterator(), false)
			.forEach(image -> imageDtoList.add(ImageUtils.toImageDto(image)));
		
		return imageDtoList;
	}

	public ImageDto createImage(String caption, MultipartFile file) throws IOException {
		UUID id = UUID.randomUUID();
		
		saveFileToGcs(id, file);
		
		Image image = new Image(id.toString(), Timestamp.ofTimeSecondsAndNanos(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 0), 
				caption, ImageUtils.getFileExtension(file));
		spannerTemplate.insert(image);
		
		return ImageUtils.toImageDto(image);
	}
	
	private void saveFileToGcs(UUID id, MultipartFile file) throws IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of(gcsBucketName, id.toString() + ImageUtils.getFileExtension(file));
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, file.getBytes());
	}
}
