package com.toyproject.board.controller;

import com.toyproject.board.dto.MemberDTO;
import com.toyproject.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String saveForm() {
        return "/member/save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        int saveResult = memberService.save(memberDTO); //저장된 정보를 int 타입으로 리턴 받고
        log.info("MemberDTO = " + memberDTO);
        if (saveResult > 0) { //저장이 되었으면 가입성공
            return "/member/login";
        } else { //그렇지 않으면 가입 실패
            return "/member/save";
        }
    }

    @GetMapping("/member/info")
    public String infoForm() {
        return "/member/info";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "/member/login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginId", memberDTO.getMemberId());
//            model.addAttribute("loginId", memberDTO.getMemberId());
            return "redirect:/list";
//            return "/member/main";
        } else {
            return "/member/login";
        }
    }

    @GetMapping("/member/logout")
    public String logOut(HttpSession session) {
        session.setAttribute("loginId", null);
        return "redirect:/list";
    }
}
