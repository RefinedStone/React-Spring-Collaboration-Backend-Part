package com.example.ReactSpringCollaborationProject.post;


import com.example.ReactSpringCollaborationProject.aws_s3.S3UploadUtil;
import com.example.ReactSpringCollaborationProject.global.dto.ResponseDto;
import com.example.ReactSpringCollaborationProject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/dev")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final S3UploadUtil s3UploadUtil;

    //모든 글 읽어 오기
    @GetMapping("/auth/post")
    public ResponseDto<?> getAllpost() {
        return ResponseDto.success(postService.getAllpost());
    }

    //글 쓰기
    @PostMapping("/post")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.createPost(postRequestDto, userDetails.getAccount()));
    }

    //글 수정
    @PutMapping("/post/{id}")
    public ResponseDto<?> updatePost(@RequestBody PostRequestDto requestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.updatePost(requestDto, id, userDetails.getAccount()));
    }

    //글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.deletePost(id, userDetails.getAccount()));
    }

}
