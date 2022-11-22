package com.newimgur.image.service;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.newimgur.image.model.Image;

@Service
public class ImageService {
   public Image getImage() {
        UUID uuid = UUID.randomUUID();
        return new Image(uuid, ZonedDateTime.now(), "Hello");
   }
}
