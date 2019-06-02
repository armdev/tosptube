package io.project.app.repositories;


import io.project.app.domain.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author armena
 */
@Component
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailAndPassword(String email, String password);

}
