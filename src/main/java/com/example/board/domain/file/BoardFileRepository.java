package com.example.board.domain.file;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardFileRepository {

    private final EntityManager em;

    public void save(BoardFile boardFile) {
        em.persist(boardFile);
    }
}
