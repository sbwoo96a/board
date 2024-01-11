package com.example.board.domain.comment;

import com.example.board.domain.post.Post;
import com.example.board.domain.post.PostRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepositoryV2 postRepository;
    private final CommentRepository commentRepository;

    //댓글 작성
    @Transactional
    public Long writeComment(CommentDTO request, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다"));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(request.getContent());

        commentRepository.save(comment);
        return comment.getId();
    }

    //댓글 목록 가져 오기
    public List<CommentDTO> commentList(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream().map(comment ->
                new CommentDTO(comment.getId(), comment.getContent())).collect(Collectors.toList());

    }

    //댓글 수정
    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
