package com.twitter.clone.service

import com.twitter.clone.model.Comment
import com.twitter.clone.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService {

    @Autowired
    CommentRepository commentRepository

    Comment createComment(Comment comment) {
        commentRepository.save(comment)
    }

    Optional<Comment> getCommentById(String commentId) {
        commentRepository.findById(commentId)
    }

    List<Comment> getCommentsByPostId(String postId) {
        commentRepository.findByPostId(postId)
    }

    void deleteComment(String commentId) {
        commentRepository.deleteById(commentId)
    }
}
