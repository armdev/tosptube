package io.project.app.services;

import io.project.app.domain.User;
import io.project.app.repositories.UserRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> userLogin(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
