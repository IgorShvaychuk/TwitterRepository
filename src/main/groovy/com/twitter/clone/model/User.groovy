package com.twitter.clone.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {
    @Id
    String id
    String username
    String password
    String email
    Set<String> followers = new HashSet<>()
    Set<String> following = new HashSet<>()
}
