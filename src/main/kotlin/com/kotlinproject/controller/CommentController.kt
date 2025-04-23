package com.kotlinproject.controller

import com.kotlinproject.PostService.CommentService
import com.kotlinproject.dto.CommentDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CommentController (
    private val commentService: CommentService
) {
    // 댓글 등록
    @PostMapping("/posts/{postId}/comments")
    fun addComment(@PathVariable postId: Long, @ModelAttribute commentDto: CommentDto): String {
        if (commentDto.content.isBlank() || commentDto.author.isBlank()) {
            throw IllegalArgumentException("댓글 내용과 작성자는 필수입니다.")
        }
        commentService.addComment(postId, commentDto)
        return "redirect:/posts/$postId"
    }

    // 댓글 삭제
    @PostMapping("/comments/{id}/delete")
    fun deleteComment(@PathVariable id: Long, @RequestParam postId: Long): String {
        commentService.deleteComment(id)
        return "redirect:/posts/$postId"
    }

    // 댓글 수정
    @PostMapping("/comments/{id}/edit")
    fun updateComment(
        @PathVariable id: Long,
        @RequestParam postId: Long,
        @ModelAttribute updateComment: CommentDto,
        @ModelAttribute commentDto: CommentDto
    ): String {
        if (commentDto.content.isBlank() || commentDto.author.isBlank()) {
            throw IllegalArgumentException("댓글 내용과 작성자는 필수입니다.")
        }
        commentService.updateComment(id, updateComment)
        return "redirect:/posts/$postId"
    }
}