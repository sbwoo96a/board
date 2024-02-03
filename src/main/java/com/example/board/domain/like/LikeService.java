package com.example.board.domain.like;

import com.example.board.domain.board.Board;
import com.example.board.domain.board.BoardJpaRepository;
import com.example.board.domain.member.Member;
import com.example.board.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardJpaRepository boardRepository;

    @Transactional
    public void like(Long boardId, String loginMember) {
        Member member = memberRepository.findByLoginId(loginMember);
        Board board = boardRepository.findById(boardId);
        board.updateLikeCount(1);
        Like like = Like.toLikeEntity(member, board);
        likeRepository.save(like);
    }

    @Transactional
    public void disLike(Long boardId, String loginMember) {
        likeRepository.deleteByMemberAndBoard(boardId, loginMember);
        Board board = boardRepository.findById(boardId);
        board.updateLikeCount(-1);
    }

    public boolean isLiked(Long boardId, String loginMember) {

        if(likeRepository.existsByMemberAndBoard(boardId, loginMember).isEmpty()) {
            return false;
        } else {
            return true;
        }

    }
}