package com.example.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updatePost(PostForm form) {
        this.title = form.getTitle();
        this.content = form.getContent();
    }

    public static Post toPostEntity(PostForm form) {
        return Post.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .build();
    }
}
