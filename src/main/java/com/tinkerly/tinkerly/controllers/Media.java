package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.services.ImageCleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@CrossOrigin
public class Media {

    private static final String UPLOAD_DIR = "/home/ubuntu/cdn/";

    private ImageCleanup imageCleanup;

    @Autowired
    public void setImageCleanup(ImageCleanup imageCleanup) {
        this.imageCleanup = imageCleanup;
    }

    @PostMapping("/media/save")
    public EndpointResponse<String> saveMedia(@RequestParam("image") MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return EndpointResponse.failed("Image is empty!");
        }

        try {
            UUID imageUUID = UUID.randomUUID();
            String filePath = UPLOAD_DIR + imageUUID.toString();

            Files.createDirectories(Paths.get(UPLOAD_DIR));
            image.transferTo(new File(filePath));

            imageCleanup.addImage(imageUUID.toString());

            return EndpointResponse.passed(imageUUID.toString());
        } catch (IOException e) {
            return EndpointResponse.failed("File upload failed!");
        }
    }
}
