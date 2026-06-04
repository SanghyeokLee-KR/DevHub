package com.icia.devhub.controller;

import com.icia.devhub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PayPointController {

    private final MemberService msvc;

    @GetMapping("/pay")
    public String signup() {
        return "pointPayment";
    }

}