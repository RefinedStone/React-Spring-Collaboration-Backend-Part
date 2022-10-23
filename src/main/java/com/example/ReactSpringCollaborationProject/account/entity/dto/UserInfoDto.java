package com.example.ReactSpringCollaborationProject.account.entity.dto;

import com.example.ReactSpringCollaborationProject.account.entity.Account;
import com.example.ReactSpringCollaborationProject.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
public class UserInfoDto {

    private String email;
    private String nickname;

    private List<Post> post;

    public UserInfoDto(Account account) {
        this.email = account.getEmail();
        this.nickname = account.getNickname();
    }

}
