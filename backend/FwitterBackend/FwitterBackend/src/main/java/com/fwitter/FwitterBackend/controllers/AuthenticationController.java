package com.fwitter.FwitterBackend.controllers;

import com.fwitter.FwitterBackend.models.ApplicationUser;
import com.fwitter.FwitterBackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    // goto: http://localhost:8080/auth/register

    @PostMapping("/register")
    public ApplicationUser registerUser(
            @RequestBody ApplicationUser user
    ) {
        return userService.registerUser(user);
    }



}
