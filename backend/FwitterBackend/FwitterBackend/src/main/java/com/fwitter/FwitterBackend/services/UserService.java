package com.fwitter.FwitterBackend.services;

import com.fwitter.FwitterBackend.dto.user.UserRequestDTO;
import com.fwitter.FwitterBackend.exceptions.EmailAlreadyTakenException;
import com.fwitter.FwitterBackend.exceptions.EmailFailedToSendException;
import com.fwitter.FwitterBackend.exceptions.IncorrectVerificationCodeException;
import com.fwitter.FwitterBackend.exceptions.UserDoesNotExistsException;
import com.fwitter.FwitterBackend.models.ApplicationUser;
import com.fwitter.FwitterBackend.models.Role;
import com.fwitter.FwitterBackend.repositories.RoleRepository;
import com.fwitter.FwitterBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;


    public ApplicationUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserDoesNotExistsException::new);
    }

    public ApplicationUser updateUser(ApplicationUser user) {
        try {
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public ApplicationUser registerUser(UserRequestDTO ro){

        ApplicationUser user = new ApplicationUser();
        user.setFirstName(ro.getFirstName());
        user.setLastName(ro.getLastName());
        user.setEmail(ro.getEmail());
        user.setDateOfBirth(ro.getDateOfBirth());
        String name = user.getFirstName() + user.getLastName();
        boolean nameTaken = true;
        String tempName = "";
        while(nameTaken){
            tempName = generateUsername(name);

            if(userRepository.findByUsername(tempName).isEmpty()){
                nameTaken = false;
            }
        }
        user.setUsername(tempName);

        Set<Role> roles = user.getAuthorities();
        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new EmailAlreadyTakenException();
        }
    }


    public void generateEmailVerification(String username) {

        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(UserDoesNotExistsException::new);


        user.setVerification(generateVerificationNumber());
        try {
            mailService.sendEmail(user.getEmail(), "Your Verification Code", "Here is your verification code: "+user.getVerification() );
            userRepository.save(user);

        } catch (Exception e) {
            throw new EmailFailedToSendException();
        }
        userRepository.save(user);
    }

    public ApplicationUser verifyEmail(String username, Long code) {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(UserDoesNotExistsException::new);

        if(code.equals(user.getVerification())){
            user.setEnabled(true);
            user.setVerification(null);
            return userRepository.save(user);
        }else {
            throw new IncorrectVerificationCodeException();
        }
    }

    public ApplicationUser setPassword(String username, String password) {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(UserDoesNotExistsException::new);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }


    private String  generateUsername(String name){
        long generatedNumber = (long) Math.floor(Math.random() * 1_000_000_000);
        return name + generatedNumber;
    }

    private Long generateVerificationNumber(){
        return (long) Math.floor(Math.random() * 1_000_000_000);

    }



}
