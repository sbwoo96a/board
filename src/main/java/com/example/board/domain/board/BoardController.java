package com.example.board.domain.board;

import com.example.board.domain.comment.CommentDTO;
import com.example.board.domain.comment.CommentService;
import com.example.board.domain.like.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final LikeService likeService;

    //웹 에디터로 글쓰기
    @GetMapping("/board")
    public String editorForm() {
        return "board/editor";
    }

    @PostMapping("/board")
    public String saveBoard(@ModelAttribute BoardRequest boardRequest, HttpServletRequest request) {
        log.info("작성한 글={}", boardRequest);
        //세션에서 로그인한 사용자 가져오기
        HttpSession session = request.getSession(false);
        String loginId = (String) session.getAttribute("loginMember");
        log.info("세션에서 가져온 로그인ID={}", loginId);
        boardService.saveBoard(boardRequest, loginId);
        return "redirect:/boards";
    }

    @GetMapping("/board/{id}")
    public String detailBoard(@PathVariable Long id, Model model, HttpSession session) {
        BoardResponse boardResponse = boardService.findBoardById(id);
        log.info("게시글 상세 보기={}", boardResponse);

        /* 댓글 목록 가져오기 */
        List<CommentDTO> comments = commentService.findComments(id);
        log.info("댓글 목록 가져오기={}", comments);

        //로그인한 사용자의 좋아요 여부 확인
        String loginMember = (String) session.getAttribute("loginMember");
        boolean liked = likeService.isLiked(id, loginMember);
        log.info("좋아여 여부={}", liked);

        model.addAttribute("boardResponse", boardResponse);
        model.addAttribute("comments", comments);
        model.addAttribute("liked", liked);
        return "board/detail";
    }

    @GetMapping("/board/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        BoardResponse boardResponse = boardService.findBoardById(id);
        model.addAttribute("boardResponse", boardResponse);
        return "board/edit";
    }

    @PostMapping("/board/{id}/edit")
    public String updateBoard(@PathVariable Long id, @ModelAttribute BoardRequest boardRequest) {
        log.info("수정 후 boardRequest={}", boardRequest);
        boardService.updateBoard(id, boardRequest);
        return "redirect:/boards";
    }

    //페이징
    @GetMapping("/boards")
    public String findBoardsByPaging(@RequestParam(value = "page", defaultValue = "1", required = false) Long page,
                                     @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                     @RequestParam(value = "searchBy", required = false) String searchBy,
                                     Model model) {
        log.info("검색 키워드={}", keyword);
        log.info("검색 조건={}", searchBy);
        Map<String, Object> paging = boardService.paging(page, keyword, searchBy);
        log.info("모든 게시글={}", paging.get("boardResponses"));
        log.info("시작 페이지={}", paging.get("startPage"));
        log.info("마지막 페이지={}", paging.get("lastPage"));
        log.info("이전 페이지 존재 여부={}", paging.get("existPrevPage"));
        log.info("다음 페이지 존재 여부={}", paging.get("existNextPage"));

        model.addAttribute("boards", paging.get("boardResponses"));
        model.addAttribute("startPage", paging.get("startPage"));
        model.addAttribute("lastPage", paging.get("lastPage"));
        model.addAttribute("existPrevPage", paging.get("existPrevPage"));
        model.addAttribute("existNextPage", paging.get("existNextPage"));
        model.addAttribute("totalPageCount", paging.get("totalPageCount"));
        model.addAttribute("currentPage", paging.get("currentPage"));
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchBy", searchBy);
        return "board/list";
    }

    @GetMapping("/board/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }
}
