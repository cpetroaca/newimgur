package com.newimgur.image.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
	
	@PostMapping(value = "/images")
	public Image createImage(@RequestParam String caption, @RequestParam MultipartFile file) {
		try {
			LOGGER.info("File name: {}", file.getContentType());
			LOGGER.info("Creating image with caption {}", caption);
			return imageService.createImage(caption, file);
		} catch (IOException ioe) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Error while saving file", ioe);
		}
	}
}
