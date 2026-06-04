package com.icia.devhub.controller;

import com.icia.devhub.service.TeamService;
import com.icia.devhub.dto.team.ResumeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TeamController {
    private final TeamService tsvc;

    @GetMapping("/team")
    public String team() {
        return "Team/TeamList";
    }

    @GetMapping("/TeamWrite")
    public String teamWrite() {
        return "Team/teamWrite";
    }

    @GetMapping("/teamView/{PId}")
    public ModelAndView teamView(@PathVariable("PId") int PId) {
        return tsvc.teamView(PId);
    }

    @GetMapping("/teamResume/{PId}")
    public ModelAndView teamResume(@PathVariable("PId") String PId) {
        return tsvc.teamResume(PId);
    }

    @PostMapping("/teamResumeSubmit")
    public ModelAndView teamResumeSubmit(@ModelAttribute ResumeDTO resume) {
        return tsvc.teamResumeSubmit(resume);
    }

    @GetMapping("/teamModifyForm/{PId}")
    public ModelAndView teamModifyForm(@PathVariable("PId") int PId) {
        return tsvc.teamModifyForm(PId);
    }

    @GetMapping("/teamDelete/{PId}")
    public ModelAndView teamDelete(@PathVariable("PId") int PId) {
        return tsvc.teamDelete(PId);
    }
}