package com.kotlinproject.Service

import com.kotlinproject.dto.CommentDto
import com.kotlinproject.entity.Comment
import com.kotlinproject.repository.CommentRepository
import com.kotlinproject.repository.PostRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {
    fun addComment(postId: Long, dto: CommentDto): Comment {
        val post = postRepository.findById(postId).orElseThrow { RuntimeException("게시글이 없습니다") }
        val comment = Comment(
            content = dto.content,
            author = dto.author,
            createdAt = LocalDateTime.now(),
            password = dto.password,
            post = post
        )
        return commentRepository.save(comment)
    }

    fun deleteComment(commentId: Long) {
        commentRepository.deleteById(commentId)
    }

    fun getCommentById(id: Long): Comment {
        return commentRepository.findById(id)
            .orElseThrow { RuntimeException("댓글이 없습니다. id=$id") }
    }

    fun updateComment(id: Long, dto: CommentDto): Comment {
        val comment = getCommentById(id)
        comment.content = dto.content
        comment.author = dto.author
        return commentRepository.save(comment)
    }
}