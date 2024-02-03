package com.example.board.domain.like;

import com.example.board.domain.board.BoardResponse;
import com.example.board.domain.board.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
@Slf4j
public class LikeApiController {

    private final LikeService likeService;
    private final BoardService boardService;

    @PostMapping("/{boardId}")
    public ResponseEntity like(@PathVariable Long boardId, HttpSession session) {
        log.info("LikeApiController.like");
        log.info("좋아요 게시글 번호={}", boardId);
        String loginMember = String.valueOf(session.getAttribute("loginMember"));
        log.info("현재 로그인한 사용자 아이디={}", loginMember);
        likeService.like(boardId, loginMember);
        BoardResponse findBoard = boardService.findBoardById(boardId);

        return new ResponseEntity<>(findBoard.getLikeCount(), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity disLike(@PathVariable Long boardId, HttpSession session) {
        log.info("LikeApiController.disLike");
        log.info("좋아요 게시글 번호={}", boardId);
        String loginMember = String.valueOf(session.getAttribute("loginMember"));
        log.info("현재 로그인한 사용자 아이디={}", loginMember);
        likeService.disLike(boardId, loginMember);
        BoardResponse findBoard = boardService.findBoardById(boardId);

        return new ResponseEntity<>(findBoard.getLikeCount(), HttpStatus.OK);
    }
}
