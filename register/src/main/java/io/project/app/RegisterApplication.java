package io.project.app;

import io.project.app.domain.User;
import io.project.app.repositories.UserRepository;
import io.project.app.services.UserService;
import io.project.app.util.PasswordHash;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("io.project.app.repositories")
@ComponentScan("io.project.app")
@EntityScan("io.project.app.domain")
@EnableEurekaClient
@EnableDiscoveryClient
public class RegisterApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(RegisterApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        Optional<User> findByEmail = userRepository.findByEmail("a@gmail.com");
        
        if (!findByEmail.isPresent()) {
            userService.save(new User("Adam", "Trust", "a@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "MALE", 25, new Date(), 1));
        }

        Optional<User> findByEmail1 = userRepository.findByEmail("b@gmail.com");
        if (!findByEmail1.isPresent()) {
            userService.save(new User("Ben", "Red", "b@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "MALE", 35, new Date(), 1));
        }

        Optional<User> findByEmail2 = userRepository.findByEmail("c@gmail.com");
        if (!findByEmail2.isPresent()) {
            userService.save(new User("Dana", "White", "c@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "FEMALE", 33, new Date(), 1));
        }

        Optional<User> findByEmail3 = userRepository.findByEmail("d@gmail.com");
        if (!findByEmail3.isPresent()) {
            userService.save(new User("Lina", "Ross", "d@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "FEMALE", 22, new Date(), 1));
        }

    }
}
