package com.example.board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //게시글 저장
    public void save(Post post) {
        em.persist(post);
    }

    //Id로 게시글 찾기
    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    //모든 게시글 가져오기
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

    //게시글 삭제
    public void delete(Long id) {
        Post findPost = findById(id);
        em.remove(findPost);
    }
}
