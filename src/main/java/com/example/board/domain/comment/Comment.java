package com.example.board.domain.comment;

import com.example.board.JpaBaseEntity;
import com.example.board.domain.board.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private String writer;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean deleteYn;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();


    public Comment(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public static Comment toCommentEntity(CommentDTO commentDTO) {
        return new Comment(commentDTO.getCommentContent(), commentDTO.getCommentWriter());
    }

    public void createComment(Board board) {
        this.board = board;
    }

    public void createReply(Board board, Comment comment) {
        this.board = board;
        this.parent = comment;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void deleteComment() {
        this.deleteYn = true;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", deleteYn=" + deleteYn +
                ", child=" + child +
                '}';
    }
}
