package com.kotlinproject.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var content: String,
    var author: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updateAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var password: String = "",

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf()
)