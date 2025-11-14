package com.codelink.controller;

import com.codelink.dto.PostCreateDTO;
import com.codelink.dto.PostDTO;
import com.codelink.security.UserDetailsImpl;
import com.codelink.service.PostService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostCreateDTO dto,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        PostDTO created = postService.createPost(dto, user.getId());
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> listPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.listPosts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id,
                                              @Valid @RequestBody PostCreateDTO dto,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        PostDTO updated = postService.updatePost(id, dto, user.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        postService.deletePost(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.likePost(id));
    }
}
