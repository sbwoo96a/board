package com.example.board.domain.file;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostFileRepository {

    private final EntityManager em;

    public void save(PostFile postFile) {
        em.persist(postFile);
    }
}
