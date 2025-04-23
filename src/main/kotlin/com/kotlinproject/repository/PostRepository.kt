package com.kotlinproject.repository

import com.kotlinproject.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findByTitleContainingOrContentContaining(
        title: String,
        content: String,
        pageable: Pageable
    ): Page<Post>
    fun findFirstByIdLessThanOrderByIdDesc(id: Long): Post?
    fun findFirstByIdGreaterThanOrderByIdAsc(id: Long): Post?
    fun findAllByOrderByIdAsc(): List<Post>
}