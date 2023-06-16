package net.fgrprojekt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Seed your data here
        Role userRole = createRoleIfNotFound("USER");
        Role adminRole = createRoleIfNotFound("ADMIN");

        User adminUser = new User("admin@example.com", passwordEncoder.encode("admin"), "Admin", "User");
        adminUser.addRole(adminRole);

        userRepository.save(adminUser);
    }

    private Role createRoleIfNotFound(String roleName) {
        Optional<Role> role = Optional.ofNullable(roleRepository.findByName(roleName));
        if (role.isEmpty()) {
            return roleRepository.save(new Role(roleName));
        }
        return role.get();
    }
}
