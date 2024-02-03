package com.example.board.domain.comment;

import com.example.board.domain.board.Board;
import com.example.board.domain.board.BoardJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentService {

    private final BoardJpaRepository boardJpaRepository;
    private final CommentRepository commentRepository;

    //댓글 작성
    @Transactional
    public Long writeComment(CommentDTO commentDTO) {
        Board findBoard = boardJpaRepository.findById(commentDTO.getBoardId());
        Comment comment = Comment.toCommentEntity(commentDTO);

        if (commentDTO.getParentId() == null) {
            comment.createComment(findBoard);
        } else {
            Comment parentComment = commentRepository.findById(commentDTO.getParentId());
            comment.createReply(findBoard, parentComment);
        }

        return commentRepository.save(comment);
    }

    //댓글 목록 가져오기
    public List<CommentDTO> findComments(Long boardId) {
        Board board = boardJpaRepository.findById(boardId);
        List<Comment> parents = commentRepository.findByParentCommentIsNull(board);
        log.info("부모 댓글들={}", parents);

        List<CommentDTO> comments = new ArrayList<>();

        for (Comment parent : parents) {
            comments.add(CommentDTO.toCommentDTO(parent, boardId));
            if(!parent.getChild().isEmpty()) {
                buildCommentHierarchy(comments, parent, boardId);
            }
        }

        return comments;
    }

    private void buildCommentHierarchy(List<CommentDTO> comments, Comment parent, Long boardId) {
        for (Comment child : parent.getChild()) {
            if(!child.isDeleteYn()) {
                comments.add(CommentDTO.toCommentDTO(child, boardId));
                if (!child.getChild().isEmpty()) {
                    buildCommentHierarchy(comments, child, boardId);
                }
            }
        }
    }


    //댓글 수정
    @Transactional
    public void updateComment(CommentDTO commentDTO) {
        Comment findComment = commentRepository.findById(commentDTO.getId());
        findComment.updateComment(commentDTO.getCommentContent());
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId);
        findComment.deleteComment();
    }
}
