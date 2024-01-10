package com.example.board.domain.file;

import com.example.board.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_file_id")
    private Long id;

    private String originalFileName;
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostFile(String originalFileName, String storedFileName, Post post) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.post = post;
    }

    public static PostFile toPostFileEntity(Post post, String originalFileName, String storedFileName) {
        return PostFile.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .post(post)
                .build();
    }
}
