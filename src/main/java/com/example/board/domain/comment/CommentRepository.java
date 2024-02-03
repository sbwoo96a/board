package com.example.board.domain.comment;

import com.example.board.domain.board.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findByParentCommentIsNull(Board board) {
        return em.createQuery("select c from Comment c where c.board.id = :boardId and c.deleteYn = false and c.parent is null", Comment.class)
                .setParameter("boardId", board.getId())
                .getResultList();
    }
}
