package com.example.ReactSpringCollaborationProject.post;

import com.example.ReactSpringCollaborationProject.Timestamped;
import com.example.ReactSpringCollaborationProject.account.entity.Account;
import com.example.ReactSpringCollaborationProject.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Post extends Timestamped {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private String contents;

    @Column(nullable = true)
    private String email;

    @JsonIgnore //JPA 순환참조
    @ManyToOne
    @JoinColumn(name = "account_Id")
    private Account account;


    //One post to Many comment
    @OneToMany(mappedBy = "post")
    private List<Comment> comment;



}