package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String userId) {
        User userFromBackend = userRepository
                .findById(userId)
                .map(userRecord -> new User(userRecord.getUserId(), userRecord.getFirstName(),
                        userRecord.getLastName()))
                .orElse(null);

        return userFromBackend;
    }

    public User addNewUser(User user) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(user.getUserId());
        userRecord.setFirstName(user.getFirstName());
        userRecord.setLastName(user.getLastName());
        userRepository.save(userRecord);
        return user;
    }
}
