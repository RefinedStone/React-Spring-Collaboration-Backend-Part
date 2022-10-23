package com.example.ReactSpringCollaborationProject.comment.dto;

import com.example.ReactSpringCollaborationProject.comment.entity.Comment;
import lombok.Getter;
import java.util.List;

@Getter
public class CommentResponseDto {

    private String comments;
    private List<Comment> commentList;
    private Long postId;
    private String success;

    public CommentResponseDto(Comment comment) {
        this.comments = comment.getComments();
    }
    public CommentResponseDto(String success){
        this.success = success;
    }
    public CommentResponseDto(List<Comment> commentList){
        this.commentList = commentList;
    }
}