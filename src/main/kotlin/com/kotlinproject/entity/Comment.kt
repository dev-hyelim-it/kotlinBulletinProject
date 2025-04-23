package com.kotlinproject.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var content: String,
    var author: String,
    val createdAt : LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post
)