package com.fwitter.FwitterBackend.services;

import com.fwitter.FwitterBackend.models.ApplicationUser;
import com.fwitter.FwitterBackend.models.Role;
import com.fwitter.FwitterBackend.repositories.RoleRepository;
import com.fwitter.FwitterBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public ApplicationUser registerUser(ApplicationUser user){
        Set<Role> roles = user.getAuthorities();
        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        return userRepository.save(user);
    }

}
