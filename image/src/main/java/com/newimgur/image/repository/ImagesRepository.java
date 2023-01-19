package com.newimgur.image.repository;

import org.springframework.cloud.gcp.data.spanner.repository.SpannerRepository;
import org.springframework.stereotype.Repository;

import com.newimgur.image.model.Image;

@Repository
public interface ImagesRepository extends SpannerRepository<Image, String> {
}
	