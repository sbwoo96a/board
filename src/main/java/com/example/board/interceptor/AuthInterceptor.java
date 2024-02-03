package com.example.board.interceptor;

import com.example.board.domain.board.BoardResponse;
import com.example.board.domain.board.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final BoardService boardService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("권한 체크 요청 {}", requestURI);

        //게시글 id 가져오기
        String[] path = requestURI.split("/");
        Long boardId = Long.parseLong(path[2]);
        log.info("권한 체크 요청시 게시글 id={}", boardId);

        //가져온 id로 게시글 정보 가져오기
        BoardResponse findBoard = boardService.findBoardById(boardId);

        //세션에서 사용자 정보 가져오기(로그인ID)
        HttpSession session = request.getSession(false);

        if (session == null) {
            request.setAttribute("isAuthor", false);
        } else {
            String currentLoginId = (String) session.getAttribute("loginMember");
            //현재 사용자와 작성자를 비교하여 권한 확인
            boolean isAuthor = currentLoginId != null && currentLoginId.equals(findBoard.getBoardWriter());

            request.setAttribute("isAuthor", isAuthor);
        }
        return true;
    }
}
