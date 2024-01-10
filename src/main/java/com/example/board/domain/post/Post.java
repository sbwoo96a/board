package com.example.board.domain.post;

import com.example.board.domain.file.PostFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private int fileAttached; //1 or 0

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostFile> postFiles = new ArrayList<>();

    @Builder
    public Post(String title, String content, int fileAttached) {
        this.title = title;
        this.content = content;
        this.fileAttached = fileAttached;
    }

    public static Post toPostFileEntity(PostForm form) {
        return Post.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .fileAttached(1)
                .build();
    }

    public void updatePost(PostForm form) {
        this.title = form.getTitle();
        this.content = form.getContent();
    }

    public static Post toPostEntity(PostForm form) {
        return Post.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .fileAttached(0)
                .build();
    }
}
