package com.twitter.clone.repository

import com.twitter.clone.model.Comment
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId)
}