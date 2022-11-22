package com.newimgur.image.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.newimgur.image.model.Image;
import com.newimgur.image.service.ImageService;

@RestController
public class ImageController {

	@Autowired
	ImageService imageService;

	@GetMapping("/")
	public Image getImage() {
		return imageService.getImage();
	}
}
