package com.icia.devhub.controller;

import com.icia.devhub.service.EventService;
import com.icia.devhub.dto.event.EventDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    private final HttpSession session;

    @GetMapping("/attend")
    public String attend() {
        return "attendance";
    }

    @PostMapping("/EventAtt")
    public @ResponseBody void event(@ModelAttribute EventDTO dto) {
        eventService.event(dto);
    }

    @GetMapping("/getAttendanceDays")
    public @ResponseBody List<String> getAttendanceDays() {
        String userId = (String) session.getAttribute("loginId");
        return eventService.getAttendanceDays(userId);
    }
}
