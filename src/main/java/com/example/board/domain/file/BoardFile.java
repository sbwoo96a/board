package com.example.board.domain.file;

import com.example.board.JpaBaseEntity;
import com.example.board.domain.board.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFile extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_file_id")
    private Long id;

    private String originalFileName;
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardFile(String originalFileName, String storedFileName, Board board) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.board = board;
    }

    public static BoardFile toBoardFileEntity(Board board, String originalFileName, String storedFileName) {
        return BoardFile.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .board(board)
                .build();
    }

    @Override
    public String toString() {
        return "BoardFile{" +
                "id=" + id +
                ", originalFileName='" + originalFileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                ", board=" + board +
                '}';
    }
}
