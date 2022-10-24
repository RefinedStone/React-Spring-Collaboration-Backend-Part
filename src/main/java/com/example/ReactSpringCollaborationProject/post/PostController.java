package com.example.ReactSpringCollaborationProject.post;


import com.example.ReactSpringCollaborationProject.global.dto.ResponseDto;
import com.example.ReactSpringCollaborationProject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/post")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    //모든 글 읽어 오기
    @GetMapping("/auth")
    public ResponseDto<?> getAllPost() {
        return ResponseDto.success(postService.getAllpost());
    }

    //글쓰기 + img 업로드
    @PostMapping(name = "post with s3 image upload")
    public ResponseDto<?> createPost(@RequestPart("post") PostRequestDto postRequestDto,
                                     @RequestPart(name = "files", required = false) MultipartFile imgFile,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return ResponseDto.success(postService.createPost(postRequestDto, imgFile, userDetails.getAccount()));
    }

    //글 수정
    @PutMapping("/{id}")
    public ResponseDto<?> updatePost(@RequestBody PostRequestDto requestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.updatePost(requestDto, id, userDetails.getAccount()));
    }

    //글 삭제
    @DeleteMapping("/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.deletePost(id, userDetails.getAccount()));
    }

}
