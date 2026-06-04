package com.icia.devhub.controller;

import com.icia.devhub.service.HistoryService;
import com.icia.devhub.dto.order.HistoryDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService hsvc;

    // 로그인 사용자의 열람 내역 조회
    @GetMapping("/uihistory")
    @ResponseBody
    public List<HistoryDTO> getHistory(HttpSession session) {
        String MId = (String) session.getAttribute("loginId");
        return hsvc.getHistory(MId);
    }

    // 로그인 사용자의 열람 내역 저장 후 히스토리 페이지로 리디렉션
    @PostMapping("/addHistory")
    public String addHistory(HttpSession session, @RequestParam List<Integer> pids) {
        String MId = (String) session.getAttribute("loginId");
        hsvc.saveHistory(MId, pids);
        return "redirect:/history?mid=" + MId;
    }
}
