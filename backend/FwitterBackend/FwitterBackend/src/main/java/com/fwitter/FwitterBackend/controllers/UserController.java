package com.fwitter.FwitterBackend.controllers;

import com.fwitter.FwitterBackend.exceptions.UnableToSavePhotoException;
import com.fwitter.FwitterBackend.models.ApplicationUser;
import com.fwitter.FwitterBackend.services.ImageService;
import com.fwitter.FwitterBackend.services.TokenService;
import com.fwitter.FwitterBackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final ImageService imageService;


    @GetMapping("/verify")
    public ApplicationUser verifyIdentity(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        String username = "";
        ApplicationUser user;

        if(token.substring(0,6).equals("Bearer")) {
            String strippedToken = token.substring(7);
            username = tokenService.getUsernameFromToken(strippedToken);
        }
        try {
            user = userService.getUserByUsername(username);
        }
        catch(Exception e) {
            user = null;
        }
        return user;
    }

    @PostMapping("/pfp")
    public ResponseEntity<String> uploadProfilePicture(
            @RequestParam("image") MultipartFile file
    ) throws UnableToSavePhotoException {
        String uploadImage = imageService.uploadImage(file, "pfp");
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }



}
