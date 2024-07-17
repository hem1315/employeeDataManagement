package com.edm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.edm.model.User;
import com.edm.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CommandLineRunner initializeUsers() {
        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (userRepository.count() == 0) {
                User user = new User();
                user.setEmail("user@gmail.com");
                user.setPassword(passwordEncoder.encode("user"));
                user.setFirstName("John");
                user.setLastName("Doe");
                userRepository.save(user);

                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setFirstName("Admin");
                admin.setLastName("admin");
                userRepository.save(admin);
            }
        };
    }
}
