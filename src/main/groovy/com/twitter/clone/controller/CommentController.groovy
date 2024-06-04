package com.twitter.clone.controller

import com.twitter.clone.model.Comment
import com.twitter.clone.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController {

    @Autowired
    CommentService commentService

    @PostMapping
    ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment)
        ResponseEntity.ok(createdComment)
    }

    @GetMapping("/{id}")
    ResponseEntity<Comment> getComment(@PathVariable String id) {
        commentService.getCommentById(id).map { comment ->
            ResponseEntity.ok(comment)
        }.orElseGet {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/post/{postId}")
    ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable String postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId)
        ResponseEntity.ok(comments)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id)
        ResponseEntity.noContent().build()
    }
}
