package com.twitter.clone.repository

import com.twitter.clone.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username)
}