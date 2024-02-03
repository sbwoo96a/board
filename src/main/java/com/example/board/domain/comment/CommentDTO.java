package com.example.board.domain.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDTO {

    private Long id;
    private Long boardId;
    private Long parentId;
    private String commentWriter;
    private String commentContent;
    private LocalDateTime createdDate;

    public CommentDTO(Long id, Long boardId, Long parentId, String commentWriter, String commentContent, LocalDateTime createdDate) {
        this.id = id;
        this.boardId = boardId;
        this.parentId = parentId;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.createdDate = createdDate;
    }

    public static CommentDTO toCommentDTO(Comment comment, Long boardId) {
        if(comment.getParent() == null) {
            return new CommentDTO(comment.getId(), boardId, null, comment.getWriter(), comment.getContent(), comment.getCreatedDate());
        } else {
            return new CommentDTO(comment.getId(), boardId, comment.getParent().getId(), comment.getWriter(), comment.getContent(), comment.getCreatedDate());
        }
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", parentId=" + parentId +
                ", commentWriter='" + commentWriter + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
