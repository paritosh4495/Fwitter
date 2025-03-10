package com.fwitter.FwitterBackend.controllers;

import com.fwitter.FwitterBackend.exceptions.FollowerException;
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

import java.util.LinkedHashMap;
import java.util.Set;

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

    @PutMapping("/")
    public ApplicationUser updateUser(
            @RequestBody ApplicationUser u
    ){
        return userService.updateUser(u);
    }


    @ExceptionHandler({FollowerException.class})
    public ResponseEntity<String> handleFollowerException() {
        return new ResponseEntity<String>("Users Cannot Follow Themselves", HttpStatus.FORBIDDEN);
    }

    @PutMapping("/follow")
    public Set<ApplicationUser> followUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody LinkedHashMap<String,String> body
            ) throws FollowerException {
        String loggedInUser = tokenService.getUsernameFromToken(token);
        String followedUser = body.get("followedUser");

        return userService.followUser(loggedInUser,followedUser);
    }


    @GetMapping("/following/{username}")
    public Set<ApplicationUser> getFollowingList(
            @PathVariable("username") String username
    ) {
        return userService.retrieveFollowingList(username);
    }



    @GetMapping("/followers/{username}")
    public Set<ApplicationUser>getFollowersList(
            @PathVariable("username") String username
    ) {
        return userService.retrieveFollowersList(username);
    }





}
