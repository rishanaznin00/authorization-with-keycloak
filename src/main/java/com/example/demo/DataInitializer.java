package com.example.demo;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User superAdmin = new User(
                        "superadmin",
                        "superadmin@example.com",
                        Set.of("SUPER_ADMIN")
                );

                User admin = new User(
                        "admin",
                        "admin@example.com",
                        Set.of("ADMIN")
                );

                User supporter = new User(
                        "supporter",
                        "supporter@example.com",
                        Set.of("SUPPORTER")
                );

                User risha = new User(
                        "risha",
                        "rishanaznin@gmail.com",
                        Set.of("SUPER_ADMIN")
                );

                userRepository.save(superAdmin);
                userRepository.save(admin);
                userRepository.save(supporter);
                userRepository.save(risha);

                System.out.println("✅ Default users inserted successfully.");
            } else {
                System.out.println("ℹ️ Users already exist, skipping initialization.");
            }
        };
    }
}

