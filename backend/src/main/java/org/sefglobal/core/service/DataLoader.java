package org.sefglobal.core.service;

import org.sefglobal.core.model.User;
import org.sefglobal.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        // Add default user on startup if there's no user is available on DB
        if(userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt()));
            userRepository.save(user);
        }
    }
}
