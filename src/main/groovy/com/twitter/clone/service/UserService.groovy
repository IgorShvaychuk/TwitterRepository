package com.twitter.clone.service

import com.twitter.clone.model.User
import com.twitter.clone.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    UserRepository userRepository

    User createUser(User user) {
        userRepository.save(user)
    }

    Optional<User> getUserById(String userId) {
        userRepository.findById(userId)
    }

    User updateUser(String userId, User user) {
        Optional<User> existingUser = userRepository.findById(userId)
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get()
            updatedUser.username = user.username
            updatedUser.email = user.email
            userRepository.save(updatedUser)
        }
    }

    void deleteUser(String userId) {
        userRepository.deleteById(userId)
    }

    List<User> getAllUsers() {
        userRepository.findAll()
    }
}
