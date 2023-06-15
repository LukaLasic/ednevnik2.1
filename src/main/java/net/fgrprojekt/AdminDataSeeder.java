package net.fgrprojekt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminDataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AdminDataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByFirstName("Admin") == null) {
            Role adminRole = roleRepository.findByName("Admin");

            if (adminRole == null) {
                // Create admin role if it doesn't exist
                adminRole = new Role("Admin");
                roleRepository.save(adminRole);
            }

            User admin = new User();
            admin.setFirstName("Admin");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            admin.setRoles(Collections.singleton(adminRole));

            userRepository.save(admin);
        }
    }
}

