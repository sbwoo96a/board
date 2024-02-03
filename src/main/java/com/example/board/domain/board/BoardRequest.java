package com.example.board.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardRequest {


    private String boardWriter;
    private String boardTitle;
    private String boardContent;

    @Builder
    public BoardRequest(String boardWriter, String boardTitle, String boardContent) {
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}