package io.project.app.repositories;

import io.project.app.domain.YoutubeVideo;
import java.util.List;
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
public interface SearchRepository extends MongoRepository<YoutubeVideo, String> {

    Optional<List<YoutubeVideo>> findBySearchKey(String key);    

}
