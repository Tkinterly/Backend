package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.services.ImageCleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.UUID;

@RestController
@CrossOrigin
public class Media {

    private static final String UPLOAD_DIR = "/home/spuggle/cdn/";

    private ImageCleanup imageCleanup;

    @Autowired
    public void setImageCleanup(ImageCleanup imageCleanup) {
        this.imageCleanup = imageCleanup;
    }

    public static class ImageRequest {
        public String image;
    }

    @PostMapping("/media/save")
    public EndpointResponse<String> saveMedia(@RequestBody ImageRequest imageRequest) throws IOException {
        try {
            UUID imageUUID = UUID.randomUUID();
            String filePath = UPLOAD_DIR + imageUUID.toString() + ".png";
            byte[] decodedBytes = Base64.getDecoder().decode(imageRequest.image);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return EndpointResponse.failed("Unsupported image format or corrupted file");
            }

            File imageFile = new File(filePath);
            ImageIO.write(image, "png", imageFile);

            imageCleanup.addImage(imageUUID.toString());

            return EndpointResponse.passed(imageUUID.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return EndpointResponse.failed("File upload failed!");
        }
    }
}
