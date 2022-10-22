package com.example.ReactSpringCollaborationProject.comment.service;

import com.example.ReactSpringCollaborationProject.account.entity.Account;
import com.example.ReactSpringCollaborationProject.comment.dto.CommentRequestDto;
import com.example.ReactSpringCollaborationProject.comment.dto.CommentResponseDto;
import com.example.ReactSpringCollaborationProject.comment.entity.Comment;
import com.example.ReactSpringCollaborationProject.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    //커멘트 생성
    public CommentResponseDto createComment(CommentRequestDto requestDto, Account account) {
        var r = new Comment(requestDto, account);
        commentRepository.save(r);
        return new CommentResponseDto(r);
    }

    // 커멘트 수정
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long id, Account account) {

        var r = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("comment is not exist"));
        if (!account.getEmail().equals(r.getEmail())) {
            throw new RuntimeException("not matched email");
        }
        r.update(requestDto);
        return new CommentResponseDto(r);
    }

    //커멘트 삭제
    public CommentResponseDto deleteComment(Long Id, Account account) {
        var r = commentRepository.findById(Id).orElseThrow(
                () -> new RuntimeException("comment is not exist"));
        if (!account.getEmail().equals(r.getEmail())) {
            throw new RuntimeException("not matched email");}
        commentRepository.deleteById(Id);
        return new CommentResponseDto("Delete success");
    }
    public CommentResponseDto getAllMyComments(Account account) {
        var r = commentRepository.findAllByEmail(account.getEmail());
        return new CommentResponseDto(r);
    }
    //커멘트 읽기
    public CommentResponseDto getOneComment(Long Id, Account account) {
        var r = commentRepository.findById(Id).orElseThrow(
                () -> new RuntimeException("comment is not exist")
        );
        if (!r.getEmail().equals(account.getEmail())) {
            throw new RuntimeException("email does not match");
        }
        return new CommentResponseDto(r);
    }
}