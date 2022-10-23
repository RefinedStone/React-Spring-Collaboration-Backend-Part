package com.example.ReactSpringCollaborationProject.post;

import com.example.ReactSpringCollaborationProject.account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 모든 글 읽어오기
    public List<PostResponseDto> getAllpost() {
        var postLists = postRepository.findAllByOrderByCreatedAtDesc();
        var postDtoLists = new ArrayList<PostResponseDto>();
        for (Post postList : postLists) {
            postDtoLists.add(new PostResponseDto(postList));
        }
        return postDtoLists;
    }

    //글 쓰기
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, Account account) {
        Post post = new Post(requestDto, account);
        postRepository.save(post);
        return new PostResponseDto(post);
    }


    //글 수정
    @Transactional
    public PostResponseDto updatePost(PostRequestDto requestDto, Long id, Account account) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id 없습니다.")
        );

        if (!account.getEmail().equals(post.getEmail())) {
            throw new IllegalArgumentException("Email 불일치");
        }
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    //글 삭제
    @Transactional
    public String deletePost(Long id, Account account) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id 없습니다.")
        );
        if (!account.getEmail().equals(post.getEmail())) {
            throw new IllegalArgumentException("email 불일치");
        }
        postRepository.deleteById(id);
        return "delete success";
    }
}