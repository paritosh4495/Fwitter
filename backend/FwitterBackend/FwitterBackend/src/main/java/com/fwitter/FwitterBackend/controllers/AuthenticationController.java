package com.fwitter.FwitterBackend.controllers;

import com.fwitter.FwitterBackend.dto.user.UserRequestDTO;
import com.fwitter.FwitterBackend.exceptions.EmailAlreadyTakenException;
import com.fwitter.FwitterBackend.exceptions.EmailFailedToSendException;
import com.fwitter.FwitterBackend.exceptions.IncorrectVerificationCodeException;
import com.fwitter.FwitterBackend.exceptions.UserDoesNotExistsException;
import com.fwitter.FwitterBackend.models.ApplicationUser;
import com.fwitter.FwitterBackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    // goto: http://localhost:8080/auth/register

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailAlreadyTakenException(final EmailAlreadyTakenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(
            @RequestBody UserRequestDTO ro
    ) {
        return userService.registerUser(ro);
    }

    @ExceptionHandler({UserDoesNotExistsException.class})
    public ResponseEntity<String> handleUserDoesNotExistsException(final UserDoesNotExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/phone")
    public ApplicationUser updatePhoneNumber(
            @RequestBody LinkedHashMap<String,String> body
    ){
        String username = body.get("username");
        String phone  = body.get("phone");

        ApplicationUser user = userService.getUserByUsername(username);
        user.setPhone(phone);
        return userService.updateUser(user);

    }

    @ExceptionHandler({EmailFailedToSendException.class})
    public ResponseEntity<String> handleFailedEmail(){
        return new ResponseEntity<String>("Email Failed to Send, Try again in a moment",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/email/code")
    public ResponseEntity<String> createEmailVerification(
            @RequestBody LinkedHashMap<String,String> body
    ){
        userService.generateEmailVerification(body.get("username"));

        return new ResponseEntity<String>("Verification code generated, Email Sent!",HttpStatus.CREATED);
    }

    @ExceptionHandler({IncorrectVerificationCodeException.class})
    public ResponseEntity<String> handleIncorrectVerificationCodeException(final IncorrectVerificationCodeException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @PostMapping("/email/verfiy")
    public ApplicationUser verfiyEmail(
            @RequestBody LinkedHashMap<String,String> body
    ){
        Long code = Long.parseLong(body.get("code"));
        String username = body.get("username");

        return userService.verifyEmail(username,code);
    }


    @PutMapping("/update/password")
    public ApplicationUser updatePassword(
            @RequestBody LinkedHashMap<String,String> body
    ){
        String password = body.get("password");
        String username = body.get("username");

        return userService.setPassword(username,password);

    }


}
