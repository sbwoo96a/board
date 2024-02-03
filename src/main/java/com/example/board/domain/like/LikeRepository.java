package com.example.board.domain.like;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final EntityManager em;

    public void save(Like like) {
        em.persist(like);
    }

    public List<Like> existsByMemberAndBoard(Long boardId, String loginMember) {

        return em.createQuery("select l from Like l where l.member.loginId = :loginMember and l.board.id = :boardId", Like.class)
                .setParameter("loginMember", loginMember)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public void deleteByMemberAndBoard(Long boardId, String loginMember) {

        em.createQuery("delete from Like l where l.member.loginId = :loginMember and l.board.id = :boardId")
                .setParameter("loginMember", loginMember)
                .setParameter("boardId", boardId)
                .executeUpdate();
    }

    public int existsByMember(String loginMember) {
        return em.createQuery("select l from Like l where l.member.loginId = :loginMember", Like.class)
                .setParameter("loginMember", loginMember)
                .getFirstResult();
    }

}
