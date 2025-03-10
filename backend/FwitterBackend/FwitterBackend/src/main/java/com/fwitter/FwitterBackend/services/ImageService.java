package com.fwitter.FwitterBackend.services;

import com.fwitter.FwitterBackend.exceptions.UnableToResolvePhotoException;
import com.fwitter.FwitterBackend.exceptions.UnableToSavePhotoException;
import com.fwitter.FwitterBackend.models.Image;
import com.fwitter.FwitterBackend.repositories.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;

    private static final File DIRECTORY = new File("H:\\Projects\\Fwitter\\backend\\FwitterBackend\\FwitterBackend\\img");

    private static final String URL = "http://localhost:8080/images/";

    public String uploadImage(MultipartFile file, String prefix) throws UnableToSavePhotoException {
        try {
            // the content type form the request looks something like
            // img/jpeg
            String extension = "."+file.getContentType().split("/")[1];
            File img = File.createTempFile(prefix, extension, DIRECTORY);
            file.transferTo(img);

            String imageURL = URL+img.getName();

            Image i = new Image(img.getName(),file.getContentType(),img.getPath(), imageURL);

            Image saved = imageRepository.save(i);
            return "File Uploaded Successfully!" + img.getName();
        }
        catch (IOException e){
            throw new UnableToSavePhotoException();
        }

    }

    public byte[] downloadImage(String fileName) throws UnableToResolvePhotoException {
        try {
            Image image = imageRepository.findByImageName(fileName)
                    .get();
            String filePath = image.getImagePath();
            byte[] imageBytes = Files.readAllBytes(new File(filePath).toPath());
            return imageBytes;

        } catch (Exception e){
            throw new UnableToResolvePhotoException();
        }
    }

    public String getImageType(String fileName) {
        Image image = imageRepository.findByImageName(fileName).get();
        return image.getImageType();
    }

}
