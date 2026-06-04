package com.icia.devhub.service;

import com.icia.devhub.dao.ProjectRepository;
import com.icia.devhub.dao.ResumeRepository;
import com.icia.devhub.dao.TeamRepository;
import com.icia.devhub.dto.team.*;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {
    private final ProjectRepository prepo;
    private final TeamRepository trepo;
    private final ResumeRepository rrepo;
    private final JavaMailSender emailSender;
    private final HttpSession session;
    private ModelAndView mav;

    public List<ProjectDTO> projectList() {
        List<ProjectEntity> team = prepo.findAll();
        List<ProjectDTO> dtoList = new ArrayList<>();
        for(int i=0; i<team.size(); i++) {
            dtoList.add(ProjectDTO.toDTO(team.get(i)));
        }
        return dtoList;
    }

    public String insertTeam(TeamDTO team) {
        TeamEntity entity = TeamEntity.toEntity(team);
        trepo.save(entity);
        return String.valueOf(entity.getTId());
    }

    public void insertProject(ProjectDTO project) {
        prepo.save(ProjectEntity.toEntity(project));
    }

    public ModelAndView teamView(int PId) {
        mav = new ModelAndView();

        Optional<ProjectEntity> project = prepo.findById(PId);
        if(project.isPresent()) {
            Optional<TeamEntity> team = trepo.findById(ProjectDTO.toDTO(project.get()).getPTId());
            if(team.isPresent()) {
                mav.setViewName("Team/teamView");
                mav.addObject("team", TeamDTO.toDTO(team.get()));
                mav.addObject("project", ProjectDTO.toDTO(project.get()));
                prepo.increaseHit(PId);
            }
        }

        return mav;
    }

    public ModelAndView teamResumeSubmit(ResumeDTO resume) {
        mav = new ModelAndView();

        // 비로그인 상태면 제출 차단 (세션 값 null 역참조로 인한 NPE 방지)
        if (session.getAttribute("loginName") == null || session.getAttribute("loginId") == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }

        rrepo.save(ResumeEntity.toEntity(resume));
        Optional<ProjectEntity> project = prepo.findById(ResumeEntity.toEntity(resume).getProject().getPId());
        MimeMessage mail = emailSender.createMimeMessage();
        if (project.isPresent()) {
            String email = ProjectDTO.toDTO(project.get()).getPEmail();

            String message = "";
            message += "<h2>이력서 제출 - " + session.getAttribute("loginName").toString() + "</h2>";
            message += "<p>" + resume.getRTitle() + "</p>";

            message += "<h3>팀 관련 항목</h3>";
            message += "<div>";
            message += "<p><strong>아이디:</strong> " + session.getAttribute("loginId").toString() + "</p>";
            message += "<p><strong>경력:</strong> " + resume.getRExperience() + "</p>";
            message += "<p><strong>학력사항:</strong> " + resume.getREducation() + "</p>";
            message += "<p><strong>보유기술:</strong> " + resume.getRSkill() + "</p>";
            message += "</div>";

            message += "<h3>성장과정</h3>";
            message += "<p>" + resume.getRGProcess() + "</p>";

            message += "<h3>성격 및 생활신조</h3>";
            message += "<p>" + resume.getRPersonality() + "</p>";

            message += "<h3>장점 및 단점</h3>";
            message += "<p>" + resume.getRAD() + "</p>";

            message += "<h3>지원동기</h3>";
            message += "<p>" + resume.getRMotive() + "</p>";

            message += "<h3>회사 업무에 대한 자세 및 포부</h3>";
            message += "<p>" + resume.getRAspiration() + "</p>";


            try {
                mail.setSubject("이력서 지원-" + session.getAttribute("loginName"));
                mail.setText(message, "UTF-8", "html");
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

                emailSender.send(mail);

                mav.setViewName("/Team/TeamList");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return mav;
    }

    public ModelAndView teamResume(String PId) {
        mav = new ModelAndView();

        mav.setViewName("Team/teamResume");
        mav.addObject("PId", PId);

        return mav;
    }

    public ModelAndView teamModifyForm(int PId) {
        mav = new ModelAndView();

        Optional<ProjectEntity> project = prepo.findById(PId);

        if(project.isPresent()) {
            Optional<TeamEntity> team = trepo.findById(project.get().getTeam().getTId());
            if(team.isPresent()) {
                mav.addObject("team", TeamDTO.toDTO(team.get()));
                mav.addObject("project", ProjectDTO.toDTO(project.get()));
                mav.setViewName("Team/teamModify");
            }
        } else {
            mav.setViewName("redirect:/index");
        }

        return mav;
    }

    public ModelAndView teamDelete(int PId) {
        mav = new ModelAndView();

        Optional<ProjectEntity> entity = prepo.findById(PId);
        if(entity.isPresent()) {
            prepo.deleteById(PId);
            trepo.deleteById(entity.get().getTeam().getTId());
            mav.setViewName("redirect:/team");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }

    public List<ProjectDTO> searchPList(String category, String keyword) {
        List<ProjectEntity> entityList = new ArrayList<>();
        List<ProjectDTO> dtoList = new ArrayList<>();

        if(category.equals("PType")) {
            entityList = prepo.findByPTypeContainingOrderByPIdDesc(keyword);
        } else if(category.equals("PContact")) {
            entityList = prepo.findByPContactContainingOrderByPIdDesc(keyword);
        } else if(category.equals("PName")) {
            entityList = prepo.findByPNameContainingOrderByPIdDesc(keyword);
        }

        for(ProjectEntity entity : entityList) {
            dtoList.add(ProjectDTO.toDTO(entity));
        }

        return dtoList;
    }

    public List<ProjectDTO> searchTList(String category, String keyword) {
        List<TeamEntity> TeamList = new ArrayList<>();

        if(category.equals("TExperience")) {
            TeamList = trepo.findByTExperienceContainingOrderByTIdDesc(keyword);
        } else if(category.equals("TContract")) {
            TeamList = trepo.findByTContractContainingOrderByTIdDesc(keyword);
        } else if(category.equals("TEducation")) {
            TeamList = trepo.findByTEducationContainingOrderByTIdDesc(keyword);
        } else if(category.equals("TSkill")) {
            TeamList = trepo.findByTSkillContainingOrderByTIdDesc(keyword);
        }

        // 검색된 팀들의 프로젝트를 중복 없이 수집 (ProjectDTO는 @Data로 equals/hashCode 생성됨)
        LinkedHashSet<ProjectDTO> result = new LinkedHashSet<>();
        for (TeamEntity team : TeamList) {
            for (ProjectEntity project : prepo.findByTeam_TIdOrderByPIdDesc(team.getTId())) {
                result.add(ProjectDTO.toDTO(project));
            }
        }

        return new ArrayList<>(result);
    }
}