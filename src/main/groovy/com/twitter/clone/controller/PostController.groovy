package com.twitter.clone.controller

import com.twitter.clone.model.Post
import com.twitter.clone.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController {

    @Autowired
    PostService postService

    @PostMapping
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post)
        ResponseEntity.ok(createdPost)
    }

    @GetMapping("/{id}")
    ResponseEntity<Post> getPost(@PathVariable String id) {
        postService.getPostById(id).map { post ->
            ResponseEntity.ok(post)
        }.orElseGet {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post)
        ResponseEntity.ok(updatedPost)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id)
        ResponseEntity.noContent().build()
    }

    @GetMapping("/feed/{userId}")
    ResponseEntity<List<Post>> getUserFeed(@PathVariable String userId) {
        List<Post> posts = postService.getUserFeed(userId)
        ResponseEntity.ok(posts)
    }

    @GetMapping
    ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts()
        ResponseEntity.ok(posts)
    }

    @PostMapping("/{postId}/like")
    ResponseEntity<Void> likePost(@PathVariable String postId, @RequestParam String userId) {
        postService.getPostById(postId).ifPresent { post ->
            post.likes.add(userId)
            postService.updatePost(postId, post)
        }
        ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{postId}/like")
    ResponseEntity<Void> unlikePost(@PathVariable String postId, @RequestParam String userId) {
        postService.getPostById(postId).ifPresent { post ->
            post.likes.remove(userId)
            postService.updatePost(postId, post)
        }
        ResponseEntity.noContent().build()
    }
}
