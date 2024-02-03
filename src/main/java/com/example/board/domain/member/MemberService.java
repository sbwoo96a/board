package com.example.board.domain.member;

import com.example.board.encrypt.EncryptHelper;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final EncryptHelper encryptHelper;

    @Transactional
    public String saveMember(MemberDTO memberDTO) {
        // dto to entity + 비밀번호 암호화
        log.info("암호화 전 비밀번호={}", memberDTO.getPassword());
        String encryptPassword = encryptHelper.encrypt(memberDTO.getPassword());
        log.info("암호화 후 비밀번호={}", encryptPassword);
        Member member = Member.toMemberEntity(memberDTO.getLoginId(), encryptPassword);
        return memberRepository.save(member);

    }

    public MemberDTO login(MemberDTO memberDTO) {
        Member findMember = memberRepository.findByLoginId(memberDTO.getLoginId());
        log.info("가입된 회원={}", findMember);

        try {
            if (!encryptHelper.isMatch(memberDTO.getPassword(), findMember.getPassword())) {
                log.info("회원 없음");
                return null;
            }
            return MemberDTO.toMemberDTO(findMember);
        } catch (Exception e) {
            throw new NoResultException("회원 없음");
        }

    }
}