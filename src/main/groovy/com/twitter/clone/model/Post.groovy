package com.twitter.clone.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
class Post {
    @Id
    String id
    String userId
    String content
    Set<String> likes = new HashSet<>()
    Set<String> comments = new HashSet<>()
}
