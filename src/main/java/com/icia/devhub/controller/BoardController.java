package com.icia.devhub.controller;

import com.icia.devhub.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bsvc;

    @GetMapping("/list")
    public String board() {
        return "board/list";
    }

    @GetMapping("/bView/{BNum}")
    public ModelAndView bView(@PathVariable("BNum") int BNum) {
        return bsvc.bView(BNum);
    }

    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    @GetMapping("/modifyForm/{BNum}")
    public ModelAndView modifyForm(@PathVariable("BNum") int BNum) {
        return bsvc.modifyForm(BNum);
    }

    @GetMapping("/bDelete/{BNum}")
    public ModelAndView bDelete(@PathVariable("BNum") int BNum) {
        return bsvc.bDelete(BNum);
    }
}