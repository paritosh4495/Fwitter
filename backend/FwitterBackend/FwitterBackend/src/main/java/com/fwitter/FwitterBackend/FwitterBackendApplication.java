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

import java.util.HashSet;

@SpringBootApplication
public class FwitterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FwitterBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserService userService, UserRepository userRepository) {

		return args -> {
			Role role = new Role();
			role.setAuthority("USER");
			roleRepository.save(role);
//			ApplicationUser user = new ApplicationUser();
//			user.setFirstName("John");
//			user.setLastName("Smith");
//			user.setEmail("john.smith@gmail.com");
//			user.setUsername("admin");
//			user.setPassword("admin");
//			userService.registerUser(user);
		};
	}

}
