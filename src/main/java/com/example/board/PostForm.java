package com.example.board;

import lombok.*;

@Getter
@ToString
@Builder
public class PostForm {

    private Long id;
    private String title;
    private String content;

    public static PostForm toPostForm(Post post) {
        return PostForm.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
