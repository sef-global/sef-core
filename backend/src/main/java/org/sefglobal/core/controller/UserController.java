package org.sefglobal.core.controller;

import org.sefglobal.core.exception.UnauthorisedException;
import org.sefglobal.core.model.User;
import org.sefglobal.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(UserController.class);
    final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/me")
    public User getCurrentUserDetails(){
        return getCurrentUser();
    }

    @PutMapping("/me/password")
    public User changePassword(@RequestBody Map<String, String> body) throws UnauthorisedException {
        User user = getCurrentUser();
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        // Compare with the old password
        if(user != null && BCrypt.checkpw(oldPassword, user.getPassword())) {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            logger.info("Password Changed successfully");
            return userRepository.save(user);
        }
        String message = "Wrong credentials provided";
        logger.error(message);
        throw new UnauthorisedException(message);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }
}
