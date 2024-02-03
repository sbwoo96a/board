package com.example.board.domain.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        @RequestParam(defaultValue = "/boards") String redirectURL,
                        HttpServletRequest request) {
        MemberDTO loginMember = memberService.login(memberDTO);
        String loginId = loginMember.getLoginId();
        if (loginId == null) {
            log.info("로그인 실패");
            return "redirect:/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginId);
        log.info("로그인 성공");
        return "redirect:" + redirectURL;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute MemberDTO memberDTO, HttpServletRequest request) {
        log.info("회원가입 정보={}", memberDTO);
        String loginId = memberService.saveMember(memberDTO);
        if (loginId.isEmpty()) {
            log.info("회원가입 실패");
            return "redirect:/signup";
        }
        log.info("회원가입 성공");
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginId);
        return "redirect:/boards";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/boards";
    }

}
