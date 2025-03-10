package com.fwitter.FwitterBackend;

import com.fwitter.FwitterBackend.models.ApplicationUser;
import com.fwitter.FwitterBackend.models.Role;
import com.fwitter.FwitterBackend.repositories.RoleRepository;
import com.fwitter.FwitterBackend.repositories.UserRepository;
import com.fwitter.FwitterBackend.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class FwitterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FwitterBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {

		return args -> {
			Role role = new Role();
			role.setAuthority("USER");
			Role r = roleRepository.save(role);

			Set<Role> roles = new HashSet<>();
			roles.add(r);

			ApplicationUser user = new ApplicationUser();
			user.setAuthorities(roles);
			user.setFirstName("Default");
			user.setLastName("User");
			user.setEmail("default@gmail.com");
			user.setUsername("default");
			user.setPassword(passwordEncoder.encode("Password!"));
			user.setEnabled(true);

			userRepository.save(user);


		};
	}

}
