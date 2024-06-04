package com.twitter.clone.controller

import com.twitter.clone.model.Post
import com.twitter.clone.service.PostService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @SpringBean
    PostService postService = Mock()

    def "test create post"() {
        given:
        Post post = new Post(content: 'Hello, world!', userId: 'testuser')
        postService.createPost(_ as Post) >> post

        when:
        ResponseEntity<Post> response = restTemplate.postForEntity("/posts", post, Post)

        then:
        response.statusCode == HttpStatus.OK
        response.body.content == 'Hello, world!'
    }

    def "test get user feed"() {
        given:
        Post post1 = new Post(content: 'First post', userId: 'testuser')
        Post post2 = new Post(content: 'Second post', userId: 'testuser')
        postService.getUserFeed('testuser') >> [post1, post2]

        when:
        ResponseEntity<List> response = restTemplate.getForEntity("/posts/feed/testuser", List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 2
    }
}
