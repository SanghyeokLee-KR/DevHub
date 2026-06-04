package com.icia.devhub.controller;

import com.icia.devhub.service.*;
import com.icia.devhub.dto.board.BoardDTO;
import com.icia.devhub.dto.board.CodeDTO;
import com.icia.devhub.dto.board.CommentDTO;
import com.icia.devhub.dto.member.MemberDTO;
import com.icia.devhub.dto.order.ProductDTO;
import com.icia.devhub.dto.team.ProjectDTO;
import com.icia.devhub.dto.team.TeamDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestfulController {
    private final MemberService msvc;
    private final CommentService csvc;
    private final BoardService bsvc;
    private final OrderService osvc;
    private final TeamService tsvc;

    @PostMapping("/resetPassword")
    public void resetPassword(@ModelAttribute MemberDTO member) {
        msvc.resetPassword(member);
    }

    @PostMapping("/passwordChange")
    public String passwordChange(@RequestParam("MEmail") String MEmail) {
        return msvc.emailCheck(MEmail, "비밀번호");
    }

    @PostMapping("/emailCheck")
    public String emailCheck(@RequestParam("MEmail") String MEmail) {
        return msvc.emailCheck(MEmail, "인증번호");
    }

    @PostMapping("/idCheck")
    public String idCheck(@RequestParam("MId") String MId) {
        return msvc.idCheck(MId);
    }

    @PostMapping("/commentList")
    public List<CommentDTO> commentList(@RequestParam("CBNum") int CBNum) {
        return csvc.commentList(CBNum);
    }

    @PostMapping("/commentWrite")
    public void commentWrite(@ModelAttribute CommentDTO comment) {
        csvc.commentWrite(comment);
    }

    @PostMapping("/commentModify")
    public void commentModify(@ModelAttribute CommentDTO comment) {
        csvc.commentModify(comment);
    }

    @PostMapping("/commentDelete")
    public void commentDelete(@ModelAttribute CommentDTO comment) {
        csvc.commentDelete(comment);
    }

    //boardList : 게시글 목록
    @PostMapping("/boardList")
    public List<BoardDTO> boardList() {
        return bsvc.boardList();
    }

    //memberProfile : 프로필 이름 가져오기
    @PostMapping("/memberList")
    public List<MemberDTO> memberList() {
        return msvc.memberList();
    }

    @PostMapping("/codeCheck")
    public List<CodeDTO> codeCheck(@RequestParam("DDNum") int DDNum) {
        return bsvc.codeCheck(DDNum);
    }

    @PostMapping("/bWrite")
    public String bWrite(@ModelAttribute BoardDTO board) {
        return bsvc.bWrite(board);
    }

    @PostMapping("/insertWrite")
    public void insertWrite(@ModelAttribute CodeDTO code) {
        bsvc.insertWrite(code);
    }
    // 주문내역 가져오기
    @GetMapping("/phistory")
    public List<ProductDTO> phistory(HttpSession session, @RequestParam String PExplain, @RequestParam String PName) {
        String MId = (String) session.getAttribute("loginId");
        return osvc.phistory(PExplain,PName,MId);
    }

    @PostMapping("/projectList")
    public List<ProjectDTO> projectList() {
        return tsvc.projectList();
    }

    @PostMapping("/insertTeam")
    public String insertTeam(@ModelAttribute TeamDTO team) {
        return tsvc.insertTeam(team);
    }

    @PostMapping("/insertProject")
    public void insertProject(@ModelAttribute ProjectDTO project) {
        project.setPDate(LocalDateTime.now());
        tsvc.insertProject(project);
    }

    @PostMapping("/bModify")
    public void bModify(@ModelAttribute BoardDTO board) {
        bsvc.bModify(board);
    }

    //searchBList : 게시글 목록 검색
    @PostMapping("/searchBList")
    public List<BoardDTO> searchBList(@RequestParam("category") String category, @RequestParam("keyword") String keyword) {
        return bsvc.searchBList(category, keyword);
    }

    @PostMapping("/searchPList")
    public List<ProjectDTO> searchPList(@RequestParam("category") String category, @RequestParam("keyword") String keyword) {
        return tsvc.searchPList(category, keyword);
    }

    @PostMapping("/searchTList")
    public List<ProjectDTO> searchTList(@RequestParam("category") String category, @RequestParam("keyword") String keyword) {
        return tsvc.searchTList(category, keyword);
    }
}
