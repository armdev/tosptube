package io.project.app.resources;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.User;
import io.project.app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/users")
@Slf4j
@Api(value = "Registration API")
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     *
     * @param user
     *
     * @return
     */
    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    @ApiOperation(value = "User Registration", notes = "Registration Controller")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful"), @ApiResponse(code = 400, message = "Exception") })    
    public ResponseEntity<?> post(@RequestBody User user) {
        Optional<User> findUserByEmail = userService.findUserByEmail(user.getEmail());
        
        if (findUserByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already present");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(user));
    }

    @GetMapping(path = "/find/user/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> get(@RequestParam(name = "userId", required = true) String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id).get());
    }

    @GetMapping(path = "/find/user/email", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> findByEmail(@RequestParam(name = "email", required = true) String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByEmail(email).get());
    }

//    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @CrossOrigin
//    @Timed
//    public ResponseEntity<?> simpleSeach(@RequestParam(name = "gender", required = false) String gender,
//            @RequestParam(name = "age", required = false) int age, @RequestParam(name = "preferences", required = false) String preferences) {
//        log.info("Search started ");
//        log.info("age " + age);
//        log.info("preferences " + preferences);
//        log.info("gender " + gender);
//        return ResponseEntity.status(HttpStatus.OK).body(userService.doSearch(gender, age, preferences));
//    }

}
