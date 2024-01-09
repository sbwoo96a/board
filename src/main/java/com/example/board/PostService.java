package com.example.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    //게시글 저장
    @Transactional
    public Long savePost(PostForm form) {
        Post post = Post.toPostEntity(form);
        postRepository.save(post);
        return post.getId();
    }

    //게시글 수정
    @Transactional
    public Long updatePost(PostForm form) {
        Post findPost = postRepository.findById(form.getId());
        findPost.updatePost(form);
        return findPost.getId();
    }

    //게시글 상세 조회
    public PostForm findPostById(Long id) {
        Post findPost = postRepository.findById(id);
        return PostForm.toPostForm(findPost);
    }

    //게시글 목록 가져오기
    public List<PostForm> findAllPosts() {
        List<PostForm> formList = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            PostForm postForm = PostForm.toPostForm(post);
            formList.add(postForm);
        }
        return formList;
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        postRepository.delete(id);
    }
}
