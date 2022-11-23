package com.newimgur.image.service;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.newimgur.image.model.Image;

@Service
public class ImageService {
   public Image getImage(String id) {
        return new Image(UUID.fromString(id), ZonedDateTime.now(), "Hello");
   }
   
   public Image createImage(String caption) {
	   UUID id = UUID.randomUUID();
	   return new Image(id, ZonedDateTime.now(), caption);
   }
}
