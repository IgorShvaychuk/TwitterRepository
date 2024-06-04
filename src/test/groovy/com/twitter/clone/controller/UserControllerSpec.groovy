package com.twitter.clone.controller

import com.twitter.clone.model.User
import com.twitter.clone.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @SpringBean
    UserService userService = Mock()

    def "test user registration"() {
        given:
        User user = new User(username: 'testuser', password: 'password')
        userService.registerUser(_) >> user

        when:
        ResponseEntity<User> response = restTemplate.postForEntity("/users/register", user, User)

        then:
        response.statusCode == HttpStatus.OK
        response.body.username == 'testuser'
    }

    def "test user login"() {
        given:
        User user = new User(username: 'testuser', password: 'password')
        userService.findByUsername('testuser') >> Optional.of(user)

        when:
        HttpHeaders headers = new HttpHeaders()
        headers.setBasicAuth('testuser', 'password')
        HttpEntity<String> entity = new HttpEntity<>(headers)
        ResponseEntity<String> response = restTemplate.exchange("/users/login", HttpMethod.POST, entity, String)

        then:
        response.statusCode == HttpStatus.OK
        response.headers.containsKey('Authorization')
    }
}
