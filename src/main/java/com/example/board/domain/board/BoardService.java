package com.example.board.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    private final BoardJpaRepository boardRepository;

    //게시글 저장
    @Transactional
    public void saveBoard(BoardRequest boardRequest, String loginId) {
        Board board = Board.saveBoardEntity(boardRequest, loginId);
        boardRepository.save(board);
    }

    //게시글 수정
    @Transactional
    public void updateBoard(Long id, BoardRequest boardRequest) {
        Board findBoard = boardRepository.findById(id);
        findBoard.updateBoard(boardRequest);
    }

    //게시글 상세 조회
    public BoardResponse findBoardById(Long id) {
        Board findBoard = boardRepository.findById(id);
        return BoardResponse.toBoardResponse(findBoard);
    }


    //게시글 목록 가져오기
    public List<BoardResponse> findAllBoards() {

        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(BoardResponse::toBoardResponse).collect(Collectors.toList());
    }

    //게시글 삭제
    @Transactional
    public void deleteBoard(Long id) {
        Board findBoard = boardRepository.findById(id);
        findBoard.deleteBoard();
    }

    //페이징
    public Map<String, Object> paging(Long page, String keyword, String searchBy) {
        int limit = 10; //화면에 보여줄 게시글 수
        int offset = (Math.toIntExact(page) - 1) * limit; //시작 인덱스 번호
        int pagination = 5; //화면 하단에 보여질 페이지 수

        //페이징 처리된 게시글 가져오기
        List<Board> boards = boardRepository.findByPaging(offset, limit, keyword, searchBy);
        List<BoardResponse> boardResponses = boards.stream().map(BoardResponse::toBoardResponse).toList();
        log.info("페이징 처리된 게시글 가져오기={}", boardResponses);
        log.info("boards size={}", boards.size());
        log.info("boardResponses size={}", boardResponses.size());

        //페이지네이션 처리하기 위한 게시글 전체 수
        int boardsCount = Math.toIntExact(boardRepository.count(keyword));
        log.info("게시글 전체 수={}", boardsCount);

        //총 페이지 수
        int totalPageCount = (int) Math.ceil((double) boardsCount / limit);
        log.info("총 페이지 수={}", totalPageCount);

        //화면에 보여질 페이지 그룹
        int pageGroup = (int)Math.ceil((double)page / pagination);

        //첫 페이지 번호
        int startPage = ((pageGroup - 1) * pagination) + 1;

        //마지막 페이지 번호
        int lastPage = 0;
        if (boardsCount == 0) {
            lastPage = startPage;
        } else {
            lastPage = Math.min(pageGroup * pagination, totalPageCount);
        }

        //이전 페이지 존재 여부 확인
        boolean existPrevPage = startPage != 1;

        //다음 페이지 존재 여부 확인
        boolean existNextPage = (lastPage * limit) < boardsCount;

        Map<String, Object> map = new HashMap<>();
        map.put("boardResponses", boardResponses);
        map.put("startPage", startPage);
        map.put("lastPage", lastPage);
        map.put("existPrevPage", existPrevPage);
        map.put("existNextPage", existNextPage);
        map.put("totalPageCount", totalPageCount);
        map.put("currentPage", page);

        return map;
    }
}
