package com.twitter.clone.repository

import com.twitter.clone.model.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserId(String userId)
}