package com.example.ReactSpringCollaborationProject.comment.dto;

import com.example.ReactSpringCollaborationProject.comment.entity.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponseDto {
    private Long postId;
    private String comments;
    private String email;

    public CommentResponseDto(Comment comment) {
        this.postId = comment.getPostId();
        this.comments = comment.getComments();
        this.email = comment.getEmail();

    }


}