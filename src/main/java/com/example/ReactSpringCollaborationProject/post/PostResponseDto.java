package com.example.ReactSpringCollaborationProject.post;

import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String contents;
    private String email;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.email = post.getEmail();

//        if (post.getComment() == null) {
//            this.commentsNum = 0;
//        } else {
//            this.commentsNum = post.getComment().size();
//        }
    }

    public PostResponseDto(String success) {
        //   this.success = success;
    }

//    public PostResponseDto(List<Post> postList) {
//        this.postList = postList;
//    }


}