package com.example.board.domain.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardJpaRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b where b.deleteYn = false", Board.class)
                .getResultList();
    }

    public List<Board> findByPaging(int offset, int limit, String keyword, String searchBy) {

        String queryString = "select b from Board b where 1=1";

        if (searchBy != null && keyword != null) {
            if ("title".equals(searchBy)) {
                queryString += " and lower(b.title) like lower(concat('%', :keyword, '%'))";
            } else if ("content".equals(searchBy)) {
                queryString += " and lower(b.content) like lower(concat('%', :keyword, '%'))";
            } else if ("author".equals(searchBy)) {
                queryString += " and lower(b.writer) like lower(concat('%', :keyword, '%'))";
            }
        }

        queryString += " order by b.id desc";

        TypedQuery<Board> query = em.createQuery(queryString, Board.class)
                .setFirstResult(offset)
                .setMaxResults(limit);

        if (searchBy != null && keyword != null) {
            query.setParameter("keyword", keyword);
        }

        return query.getResultList();
    }

    public long count(String keyword) {
        log.info("BoardJpaRepository.count={}", keyword);
        String jpql = "SELECT COUNT(b) FROM Board b ";

        if (keyword != null) {
            jpql += "WHERE LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%'))";
        }

        return em.createQuery(jpql, Long.class)
                .setParameter("keyword", keyword)
                .getSingleResult();
    }
}