package com.example.board.domain.board;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BoardResponse {

    private Long id;

    private String boardWriter;
    private String boardTitle;
    private String boardContent;

    private int viewCount;
    private int likeCount;

    private LocalDateTime createdDate;

    @Builder
    public BoardResponse(Long id, String boardWriter, String boardTitle, String boardContent, int viewCount, int likeCount, LocalDateTime createdDate) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
    }

    public static BoardResponse toBoardResponse(Board board) {

        return BoardResponse.builder()
                .id(board.getId())
                .boardWriter(board.getWriter())
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .createdDate(board.getCreatedDate())
                .viewCount(board.getViewCount())
                .likeCount(board.getLikeCount())
                .build();
    }
}
