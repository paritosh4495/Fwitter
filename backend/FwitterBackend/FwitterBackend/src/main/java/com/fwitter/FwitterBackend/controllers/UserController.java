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
        String username = tokenService.getUsernameFromToken(token);
        return userService.getUserByUsername(username);
    }

    @PostMapping("/pfp")
    public ApplicationUser uploadProfilePicture(
            @RequestParam("image") MultipartFile file,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) throws UnableToSavePhotoException {
        String username = tokenService.getUsernameFromToken(token);
        return userService.setProfileOrBannerPicture(username,file,"pfp");
    }

    @PostMapping("/banner")
    public ApplicationUser uploadBannerPicture(
            @RequestParam("image") MultipartFile file,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) throws  UnableToSavePhotoException {
        String username = tokenService.getUsernameFromToken(token);
        return userService.setProfileOrBannerPicture(username,file,"bnr");

    }
}
