package com.kotlinproject.PostService

import com.kotlinproject.dto.PostDto
import com.kotlinproject.entity.Post
import com.kotlinproject.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PostService (
    private val postRepository: PostRepository
) {

    fun createPost(dto: PostDto): Post {
        val post = Post(
            title = dto.title,
            content = dto.content,
            author = dto.author
        )
        return postRepository.save(post)
    }

    fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    fun getPostById(id: Long): Post {
        return postRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 게시글이 없습니다. id=$id") }
    }

    fun updatePost(id: Long, dto: PostDto): Post {
        val post = getPostById(id)
        post.title = dto.title
        post.content = dto.content
        post.author = dto.author
        post.updateAt = LocalDateTime.now()
        return postRepository.save(post)
    }

    fun deletePost(id: Long) {
        val post = getPostById(id)
        postRepository.delete(post)
    }

    fun searchPosts(keyword: String?, page: Int, size: Int): Page<Post> {
        val pageable: Pageable = PageRequest.of(page, size)
        return if (!keyword.isNullOrBlank()) {
            postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable)
        } else {
            postRepository.findAll(pageable)
        }
    }

    fun getPageNumberForPost(postId: Long, pageSize: Int): Int {
        val allPosts = postRepository.findAllByOrderByIdAsc() // ID 기준 전체 정렬
        val index = allPosts.indexOfFirst { it.id == postId }
        println("전체 글 개수: ${allPosts.size}, 현재 글 index: $index, 계산된 페이지: ${index / pageSize}")
        return if (index >= 0) index / pageSize else 0
    }

    fun getPreviousPost(currentId: Long): Post? {
        return postRepository.findFirstByIdLessThanOrderByIdDesc(currentId)
    }

    fun getNextPost(currentId: Long): Post? {
        return postRepository.findFirstByIdGreaterThanOrderByIdAsc(currentId)
    }
}