package io.project.app.services;

import io.project.app.domain.User;
import io.project.app.dto.UserData;
import io.project.app.repositories.UserRepository;
import io.project.app.util.CommonConstants;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private MongoTemplate mongoTemplate;

    public User save(User user) {
        user.setStatus(CommonConstants.ACTIVATED);
        User savedUser = userRepository.save(user);
        UserData userData = new UserData(savedUser.getId(), savedUser.getFirstname(), savedUser.getLastname());
        
        return savedUser;
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    public Optional<List<User>> simpleSearch(String gender, int age, String preferences) {
//        log.info("Simple stupid search started ");
//        Optional<List<User>> findByGenderOrAgeOrPreferences = userRepository.findByGenderOrAgeOrPreferences(gender, age, preferences);
//        if (findByGenderOrAgeOrPreferences.isPresent()) {
//            log.info("List size " + findByGenderOrAgeOrPreferences.get().size());
//            return findByGenderOrAgeOrPreferences;
//        }
//
//        return Optional.empty();
//    }

    public List<User> doSearch(String gender, int age, String preferences) {

        log.info("doSearch: Starting filter");
        Query query = new Query();

        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        query.addCriteria(Criteria.where("age").is(age));
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("preferences").regex(preferences),
                        Criteria.where("gender").is(gender)
                )
        );

        query.skip(0).limit(100);

        log.info("doSearch: query " + query.toString());

        List<User> resultList = mongoTemplate.find(query, User.class, "user");

        return resultList;
    }

}
