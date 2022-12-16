package com.newimgur.image.service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.Timestamp;
import com.newimgur.image.model.Image;
import com.newimgur.image.repository.ImagesRepository;

@Service
public class ImageService {
	@Autowired
	ImagesRepository imagesRepository;

	public Image getImage(String id) {
		Optional<Image> opt = imagesRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		
		return null;
	}

	public Image createImage(String caption) {
		UUID id = UUID.randomUUID();
		Image image = new Image(id.toString(), Timestamp.ofTimeMicroseconds(ZonedDateTime.now().toEpochSecond()), caption);
		
		imagesRepository.save(image);
		
		return image;
	}
}
