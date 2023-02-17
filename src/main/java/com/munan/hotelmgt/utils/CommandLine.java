package com.munan.hotelmgt.utils;

import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.model.AppUser;
import com.munan.hotelmgt.model.Role;
import com.munan.hotelmgt.repository.AppUserRepository;
import com.munan.hotelmgt.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Optional;

@Component
@Transactional
public class CommandLine implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(CommandLine.class);

    public CommandLine(RoleRepository roleRepository, AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){

        try {

            AppUser tempUser = new AppUser();
            tempUser.setUsername("admin@gmail.com");
            tempUser.setPassword("1234");

            Optional<AppUser> findUser = userRepository.findByUsername(tempUser.getUsername());

            if (findUser.isEmpty()) {
                Optional<Role> roles = roleRepository.findByName("ROLE_ADMIN");

                if (roles.isEmpty()) {
                    roles = Optional.of(roleRepository.save(new Role("ROLE_ADMIN")));
                }
                roles.ifPresent(role -> {
                    AppUser newUser = new AppUser();
                    newUser.setUsername(tempUser.getUsername());
                    newUser.setPassword(passwordEncoder.encode(tempUser.getPassword()));
                    newUser.setRoles(Collections.singleton(role));

                    userRepository.save(newUser);

                });
            }else {
                throw new AlreadyExistException(tempUser.getUsername()+" already exist");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

}

