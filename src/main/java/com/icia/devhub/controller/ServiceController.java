package com.icia.devhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ServiceController {

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/services2")
    public String services2() {
        return "services2";
    }


}
