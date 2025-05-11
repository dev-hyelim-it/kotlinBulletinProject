package com.kotlinproject.controller

import com.kotlinproject.Service.CommentService
import com.kotlinproject.dto.CommentDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

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

    @PostMapping("/comments/{id}/verify-password")
    fun verifyCommentPassword(
        @PathVariable id: Long,
        @RequestParam password: String,
        @RequestParam action: String,
        @RequestParam postId: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        println("🟢 password 확인 요청: id=$id, action=$action, postId=$postId, 입력한 비밀번호=$password")
        val comment = commentService.getCommentById(id)
        return if (comment.password == password) {
            when (action) {
                "edit" -> {
                    // 수정 폼 표시
                    redirectAttributes.addAttribute("editCommentId", id)
                    return "redirect:/posts/$postId"
                }
                "delete" -> {
                    commentService.deleteComment(id)
                    return "redirect:/posts/$postId"
                }
                else -> "redirect:/posts/$postId"
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.")
            return "redirect:/posts/$postId"
        }
    }

}