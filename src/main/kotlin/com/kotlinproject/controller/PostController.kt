package com.kotlinproject.controller

import com.kotlinproject.PostService.PostService
import com.kotlinproject.dto.CommentDto
import com.kotlinproject.dto.PostDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/posts")
class PostController (
    private val postService: PostService
) {
    // 글 목록
    @GetMapping
    fun listPosts(
        model: Model,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(defaultValue = "0") page: Int
    ): String {
        val postPage = postService.searchPosts(keyword, page, 5)
        val posts = postService.getAllPosts()
        model.addAttribute("postPage", postPage)
        model.addAttribute("keyword", keyword ?: "")
        model.addAttribute("isSearching", !keyword.isNullOrBlank())
        model.addAttribute("posts", posts)
        return "post/list"
    }

    // 글 작성 페이지
    @GetMapping("/new")
    fun showCreateForm(model: Model): String {
        model.addAttribute("postDto", PostDto("","",""))
        return "post/form"
    }

    // 글 작성 처리
    @PostMapping
    fun createPost(@ModelAttribute postDto: PostDto): String {
        postService.createPost(postDto)
        return "redirect:/posts"
    }

    // 글 상세 보기
    @GetMapping("/{id}")
    fun showPostDetail(@PathVariable id: Long,
                       @RequestParam(required = false) page: Int?,
                       @RequestParam(required = false) keyword: String?,
                       model: Model
    ): String {
        val post = postService.getPostById(id)
        val prevPost = postService.getPreviousPost(id)
        val nextPost = postService.getNextPost(id)
        val currentPage = postService.getPageNumberForPost(id, 5)
        val nextPage = nextPost?.let { postService.getPageNumberForPost(it.id, 5) }
        val prevPage = prevPost?.let { postService.getPageNumberForPost(it.id, 5) }

        model.addAttribute("post", post)
        model.addAttribute("prevPost", prevPost)
        model.addAttribute("nextPost", nextPost)
        model.addAttribute("page", currentPage)
        model.addAttribute("nextPage", nextPage)
        model.addAttribute("prevPage", prevPage)
        model.addAttribute("keyword", keyword ?: "")
        model.addAttribute("commentDto", CommentDto())
        return "post/detail"
    }

    // 글 수정 폼
    @GetMapping("/{id}/edit")
    fun showEditForm(@PathVariable id: Long,
                     @RequestParam(required = false) page: Int?,
                     @RequestParam(required = false) keyword: String?,
                     model: Model
    ): String {
        val post = postService.getPostById(id)
        val postDto = PostDto(post.title, post.content, post.author)

        model.addAttribute("postDto", postDto)
        model.addAttribute("postId", id)
        model.addAttribute("page", page)
        model.addAttribute("keyword", keyword ?: "")
        return "post/edit"
    }

    // 글 수정 처리
    @PostMapping("/{id}/edit")
    fun updatePost(@PathVariable id: Long, @ModelAttribute postDto: PostDto): String {
        postService.updatePost(id, postDto)
        return "redirect:/posts/$id"
    }

    // 글 삭제
    @PostMapping("/{id}/delete")
    fun deletePost(@PathVariable id: Long): String {
        postService.deletePost(id)
        return "redirect:/posts"
    }
}