package io.project.app.resources;

import io.project.app.services.UserService;
import io.micrometer.core.annotation.Timed;
import io.project.app.dto.Login;
import io.project.app.util.PasswordHash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/users")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;

    /**
     *
     * @param login
     * @return
     */
    @PostMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> post(@RequestBody Login login) {
        log.info("user login");
        return ResponseEntity.status(HttpStatus.OK).body(userService.userLogin(login.getEmail(), PasswordHash.hashPassword(login.getPassword())).get());
    }
    
}
