package com.twitter.clone.controller

import com.twitter.clone.model.User
import com.twitter.clone.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    UserService userService

    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.createUser(user)
        ResponseEntity.ok(createdUser)
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUser(@PathVariable String id) {
        userService.getUserById(id).map { user ->
            ResponseEntity.ok(user)
        }.orElseGet {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user)
        ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id)
        ResponseEntity.noContent().build()
    }

    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers()
        ResponseEntity.ok(users)
    }
}
