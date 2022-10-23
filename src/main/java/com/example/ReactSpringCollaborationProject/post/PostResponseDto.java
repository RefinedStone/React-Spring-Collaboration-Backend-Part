package com.example.ReactSpringCollaborationProject.post;

import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String contents;
    private String email;

    private long commentsNum;
    private List<Post> postList;
    private String success;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.email = post.getEmail();
        this.postId = post.getId();
        this.commentsNum = post.getComment().size();
    }

    public PostResponseDto(String success) {
        this.success = success;
    }

    public PostResponseDto(List<Post> postList) {
        this.postList = postList;
    }
}