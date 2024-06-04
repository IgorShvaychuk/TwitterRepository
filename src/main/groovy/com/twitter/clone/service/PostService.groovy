package com.twitter.clone.service

import com.twitter.clone.model.Post
import com.twitter.clone.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {

    @Autowired
    PostRepository postRepository

    Post createPost(Post post) {
        postRepository.save(post)
    }

    Optional<Post> getPostById(String postId) {
        postRepository.findById(postId)
    }

    Post updatePost(String postId, Post post) {
        Optional<Post> existingPost = postRepository.findById(postId)
        if (existingPost.isPresent()) {
            Post updatedPost = existingPost.get()
            updatedPost.content = post.content
            postRepository.save(updatedPost)
        }
    }

    void deletePost(String postId) {
        postRepository.deleteById(postId)
    }

    List<Post> getUserFeed(String userId) {
        postRepository.findByUserId(userId)
    }

    List<Post> getAllPosts() {
        postRepository.findAll()
    }
}
