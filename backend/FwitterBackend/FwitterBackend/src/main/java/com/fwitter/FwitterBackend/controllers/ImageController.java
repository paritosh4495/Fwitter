package com.fwitter.FwitterBackend.controllers;

import com.fwitter.FwitterBackend.exceptions.UnableToResolvePhotoException;
import com.fwitter.FwitterBackend.exceptions.UnableToSavePhotoException;
import com.fwitter.FwitterBackend.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
@CrossOrigin("*")
public class ImageController {
    private final ImageService imageService;

    @ExceptionHandler({UnableToSavePhotoException.class, UnableToResolvePhotoException.class})
    public ResponseEntity<String> handlePhotoExceptions(){
        return new ResponseEntity<>("Unable to Process the photo ", HttpStatus.NOT_ACCEPTABLE);

    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(
            @PathVariable String fileName
    ) throws UnableToResolvePhotoException {
        byte[] imageBytes = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(imageService.getImageType(fileName))).body(imageBytes);
    }
}
