package com.fwitter.FwitterBackend.services;

import com.fwitter.FwitterBackend.dto.user.UserRequestDTO;
import com.fwitter.FwitterBackend.exceptions.*;
import com.fwitter.FwitterBackend.models.ApplicationUser;
import com.fwitter.FwitterBackend.models.Image;
import com.fwitter.FwitterBackend.models.Role;
import com.fwitter.FwitterBackend.repositories.RoleRepository;
import com.fwitter.FwitterBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;


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
        user.setDateOfBirth(ro.getDob());
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser u = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found!"));

        Set<GrantedAuthority> authorities = u.getAuthorities()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet());

        UserDetails ud = new User(u.getUsername(), u.getPassword(), authorities);
        return ud;
    }

    public ApplicationUser setProfileOrBannerPicture(String username, MultipartFile file, String prefix) throws UnableToSavePhotoException {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(UserDoesNotExistsException::new);

        Image photo = imageService.uploadImage(file, prefix);

        try {
            if(prefix.equals("pfp")){
                if(user.getProfilePicture() != null && !user.getProfilePicture().getImageName().equals("defaultpfp.png")){
                    Path p = Paths.get(user.getProfilePicture().getImagePath());
                    Files.deleteIfExists(p);
                }
                user.setProfilePicture(photo);
            }
            else {
                if(user.getBannerPicture()!=null && !user.getBannerPicture().getImageName().equals("defaultbnr.png")){
                    Path p = Paths.get(user.getBannerPicture().getImagePath());
                    Files.deleteIfExists(p);
                }
                user.setBannerPicture(photo);
            }

        } catch (IOException e) {
            throw new UnableToSavePhotoException();
        }



        return userRepository.save(user);

    }

    public Set<ApplicationUser> followUser(String user, String followee ) throws FollowerException {


        if(user.equals(followee)) throw new FollowerException();

        ApplicationUser loggedInUser = userRepository.findByUsername(user)
                .orElseThrow(UserDoesNotExistsException::new);

        Set<ApplicationUser> followingList = loggedInUser.getFollowing();

        ApplicationUser followedUser = userRepository.findByUsername(followee)
                .orElseThrow(UserDoesNotExistsException::new);

        Set<ApplicationUser> followersList = followedUser.getFollowers();

        // ADD the Followed User to the following LIst

        followingList.add(followedUser);

        loggedInUser.setFollowing(followingList);

        // ADD the Current User to the followers list of the followee

        followersList.add(loggedInUser);
        followedUser.setFollowers(followersList);

        // update Both Users

        userRepository.save(loggedInUser);
        userRepository.save(followedUser);

        return loggedInUser.getFollowing();
    }


    public Set<ApplicationUser> retrieveFollowersList(String username) {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(UserDoesNotExistsException::new);

        return user.getFollowers();

    }

    public Set<ApplicationUser> retrieveFollowingList(String username) {

        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(UserDoesNotExistsException::new);

        return user.getFollowing();
    }
}
