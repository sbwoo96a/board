package com.example.board.domain.comment;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentDTO {

    private Long id;
    private String content;

    public CommentDTO(Long id, String content) {
        this.id = id;
        this.content = content;
    }
    public CommentDTO(String content) {
        this.content = content;
    }
}
