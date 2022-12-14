package com.newimgur.image.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.newimgur.image.model.Image;
import com.newimgur.image.service.ImageService;

@RestController
public class ImageController {
	private final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	ImageService imageService;

	@GetMapping("/images/{id}")
	public Image getImage(@PathVariable String id) {
		try {
			LOGGER.info("Requested image with id {}", id);
			return imageService.getImage(id);
		} catch (IllegalArgumentException iae) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, "`id` parameter has an unknown format", iae);
		}
	}
	
	@PostMapping(value = "/images" , consumes = { MediaType.APPLICATION_JSON_VALUE,
												  MediaType.MULTIPART_FORM_DATA_VALUE})
	public Image createImage(@RequestPart String caption) {
		LOGGER.info("Created image with caption {}", caption);
		return imageService.createImage(caption);
	}
}
