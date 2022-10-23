package com.example.ReactSpringCollaborationProject.post;


import com.example.ReactSpringCollaborationProject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/dev")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    //모든 글 읽어 오기
    @GetMapping("/auth/post")
    public ResponseEntity<?> getAllpost() {
        return ResponseEntity.ok(postService.getAllpost());
    }

    //글 쓰기
    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.createPost(postRequestDto,userDetails.getAccount()));
    }

    //글 수정
    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@RequestBody PostRequestDto requestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.updatePost(requestDto, id, userDetails.getAccount()));
    }

    //글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.deletePost(id,userDetails.getAccount()));
    }

}
