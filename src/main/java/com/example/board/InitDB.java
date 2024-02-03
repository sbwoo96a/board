package com.example.board;

import com.example.board.domain.board.Board;
import com.example.board.domain.board.BoardJpaRepository;
import com.example.board.domain.member.Member;
import com.example.board.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {

            for (int i = 0; i < 1000; i++) {
                Board board = new Board("" + i, "" + i, "" + i);
                em.persist(board);
            }
        }

    }

}
