package com.example.board.domain.board;

import com.example.board.JpaBaseEntity;
import com.example.board.domain.comment.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String writer;
    private String title;
    @Column(length = 3000)
    private String content;
    private int viewCount;
    private int likeCount;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean deleteYn;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public static Board saveBoardEntity(BoardRequest boardRequest, String loginId) {
        return Board.builder()
                .writer(loginId)
                .title(boardRequest.getBoardTitle())
                .content(boardRequest.getBoardContent())
                .build();
    }

    public void updateBoard(BoardRequest boardRequest) {
        this.title = boardRequest.getBoardTitle();
        this.writer = boardRequest.getBoardWriter();
        this.content = boardRequest.getBoardContent();
    }

    public void deleteBoard() {
        this.deleteYn = true;
    }

    public void updateLikeCount(int likeCount) {
        this.likeCount += likeCount;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", deleteYn=" + deleteYn +
                '}';
    }
}
