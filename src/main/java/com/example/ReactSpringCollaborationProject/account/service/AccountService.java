package com.example.ReactSpringCollaborationProject.account.service;

import com.example.ReactSpringCollaborationProject.account.entity.Account;
import com.example.ReactSpringCollaborationProject.account.entity.RefreshToken;
import com.example.ReactSpringCollaborationProject.account.entity.dto.AccountReqDto;
import com.example.ReactSpringCollaborationProject.account.entity.dto.LoginReqDto;
import com.example.ReactSpringCollaborationProject.account.repository.AccountRepository;
import com.example.ReactSpringCollaborationProject.account.repository.RefreshTokenRepository;
import com.example.ReactSpringCollaborationProject.account.service.jwt.dto.TokenDto;
import com.example.ReactSpringCollaborationProject.account.service.jwt.util.JwtUtil;
import com.example.ReactSpringCollaborationProject.comment.dto.CommentResponseDto;
import com.example.ReactSpringCollaborationProject.comment.entity.Comment;
import com.example.ReactSpringCollaborationProject.comment.repository.CommentRepository;
import com.example.ReactSpringCollaborationProject.global.dto.GlobalResDto;
import com.example.ReactSpringCollaborationProject.global.dto.ResponseDto;
import com.example.ReactSpringCollaborationProject.post.Post;
import com.example.ReactSpringCollaborationProject.post.PostRepository;
import com.example.ReactSpringCollaborationProject.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AccountService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public GlobalResDto signup(AccountReqDto accountReqDto) {
        // email 중복 검사
        if (accountRepository.findByEmail(accountReqDto.getEmail()).isPresent()) {
            throw new RuntimeException("Overlap Check");
        }

        accountReqDto.setEncodePwd(passwordEncoder.encode(accountReqDto.getPassword()));
        Account account = new Account(accountReqDto);

        accountRepository.save(account);
        return new GlobalResDto("Success signup", HttpStatus.OK.value());
    }

//    @Transactional
    public GlobalResDto login(LoginReqDto loginReqDto, HttpServletResponse response) {

        Account account = accountRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(() -> new RuntimeException("Not found Account"));

        if (!passwordEncoder.matches(loginReqDto.getPassword(), account.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(loginReqDto.getEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        //수정부분
        //return ResponseDto.success(response);
//        ResponseEntity.ok().body(response);
//        ResponseEntity.ok(response).body(response).headers(response);

        return new GlobalResDto("Success Login", HttpStatus.OK.value());


    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    public List<PostResponseDto> getMyPost(Account account) {

        List<Post> posts = postRepository.findAllByUserEmail(account.getEmail());
        List<PostResponseDto> postResponseDtos = new ArrayList<PostResponseDto>();
        for (Post post : posts) {
            postResponseDtos.add(new PostResponseDto(post));
        }
        return postResponseDtos;
    }

    public List<CommentResponseDto> getMyComment(Account account) {
        List<Comment> comments = commentRepository.findAllByEmail(account.getEmail());
        List<CommentResponseDto> commentResponseDtos = new ArrayList<CommentResponseDto>();
        for (Comment comment : comments) {
            commentResponseDtos.add(new CommentResponseDto(comment));
        }
        return commentResponseDtos;
    }
}
