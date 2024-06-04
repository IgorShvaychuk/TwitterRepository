package com.twitter.clone.controller

import com.twitter.clone.model.Comment
import com.twitter.clone.service.CommentService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @SpringBean
    CommentService commentService = Mock()

    def "test add comment"() {
        given:
        Comment comment = new Comment(content: 'Nice post!', postId: 'post1', userId: 'user1')
        commentService.addComment(_) >> comment

        when:
        ResponseEntity<Comment> response = restTemplate.postForEntity("/comments", comment, Comment)

        then:
        response.statusCode == HttpStatus.OK
        response.body.content == 'Nice post!'
    }

    def "test get post comments"() {
        given:
        Comment comment1 = new Comment(content: 'First comment', postId: 'post1', userId: 'user1')
        Comment comment2 = new Comment(content: 'Second comment', postId: 'post1', userId: 'user2')
        commentService.getCommentsByPostId('post1') >> [comment1, comment2]

        when:
        ResponseEntity<List> response = restTemplate.getForEntity("/comments/post1", List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 2
    }
}
