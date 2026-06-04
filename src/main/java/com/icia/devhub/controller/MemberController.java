package com.icia.devhub.controller;

import com.icia.devhub.dto.member.LoginDTO;
import com.icia.devhub.service.MemberService;
import com.icia.devhub.dto.member.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService msvc;
    private final HttpSession session;

    // ===== 정적 페이지 라우팅 =====
    @GetMapping("/")
    public String startPage() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/aiChat")
    public String aiChat() {
        return "aiChat";
    }

    @GetMapping("/Chat")
    public String Chat() {
        return "body/chat";
    }

    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "/member/signup";
    }

    @GetMapping("/reset")
    public String reset() {
        return "/member/reset";
    }

    @GetMapping("/mLogout")
    public String mLogout() {
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/coding")
    public String coding() {
        return "coding";
    }

    // ===== 가입 / 로그인 =====
    @PostMapping("/mJoin")
    public ModelAndView mJoin(@ModelAttribute MemberDTO member) {
        return msvc.mJoin(member);
    }

    @PostMapping("/mLogin")
    public ModelAndView mLogin(@ModelAttribute MemberDTO member) {
        return msvc.mLogin(member);
    }

    // 로그인 이력(IP·접속 시각) 저장
    @PostMapping("/log")
    public @ResponseBody void mLogin(@ModelAttribute LoginDTO dto) {
        msvc.log(dto);
    }

    // 세션 로그인 여부를 확인하는 REST API
    @RestController
    @RequestMapping("/api")
    public static class SessionController {
        private final HttpSession session;

        @Autowired
        public SessionController(HttpSession session) {
            this.session = session;
        }

        @GetMapping("/checkSession")
        public ResponseEntity<String> checkSession() {
            String loginId = (String) session.getAttribute("loginId");

            if (loginId != null) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(401).body("fail");
            }
        }
    }

    // ===== 회원 조회 / 수정 / 탈퇴 =====
    @GetMapping("/mView/{MId}")
    public ModelAndView mView(@PathVariable("MId") String MId) {
        return msvc.mView(MId);
    }

    @GetMapping("/modiForm/{MId}")
    public ModelAndView modiForm(@PathVariable("MId") String MId) {
        return msvc.modiForm(MId);
    }

    @PostMapping("/mModify")
    public ModelAndView mModify(@ModelAttribute MemberDTO member) {
        return msvc.mModify(member);
    }

    @GetMapping("/deleteForm/{MId}")
    public ModelAndView deleteForm(@PathVariable("MId") String MId) {
        return msvc.deleteForm(MId);
    }

    @PostMapping("/mDelete")
    public ModelAndView mDelete(@ModelAttribute MemberDTO member) {
        return msvc.mDelete(member);
    }

    @GetMapping("/logHistory")
    @ResponseBody
    public List<LoginDTO> getLoginHistory(HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        return msvc.getLoginHistoryByUserId(userId);
    }
}
