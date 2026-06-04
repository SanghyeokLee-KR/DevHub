package com.icia.devhub.service;

import com.icia.devhub.dao.*;
import com.icia.devhub.dto.board.BoardDTO;
import com.icia.devhub.dto.board.BoardEntity;
import com.icia.devhub.dto.board.CodeDTO;
import com.icia.devhub.dto.board.CodeEntity;
import com.icia.devhub.dto.member.LoginDTO;
import com.icia.devhub.dto.member.LoginEntity;
import com.icia.devhub.dto.member.MemberEntity;
import com.icia.devhub.dto.member.MemberDTO;
import com.icia.devhub.dto.order.ProductDTO;
import com.icia.devhub.dto.order.ProductEntity;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final JavaMailSender mailSender;
    private final PasswordEncoder pwEnc;
    private final HttpSession session;
    private ModelAndView mav;
    // 프로필 이미지 저장 경로 (실행 위치의 uploads/profile 외부 폴더 — WebConfig 가 /profile/** 로 서빙)
    Path path = Paths.get(System.getProperty("user.dir"), "uploads", "profile");

    private final MemberRepository mrepo;
    private final LoginRepository lrepo;
    private final BoardRepository brepo;
    private final CodeRepository bcrepo;
    private final CommentRepository crepo;
    private final CouponRepository cprepo;
    private final EventRepository erepo;
    private final CartRepository cartrepo;
    private final CategoryRepository cgrepo;
    private final HistoryRepository hrepo;
    private final PaymentRepository pmrepo;
    private final ProductRepository pdrepo;
    private final ProjectRepository pjrepo;
    private final ResumeRepository rrepo;
    private final TeamRepository trepo;

    // 아이디 중복 체크 (사용 가능: "OK" / 중복: "NO")
    public String idCheck(String MId) {
        String result = "";
        Optional<MemberEntity> entity = mrepo.findById(MId);
        if (entity.isPresent()) {
            result = "NO";
        } else {
            result = "OK";
        }
        return result;
    }

    // 인증번호를 메일로 발송하고 그 번호를 반환 (check: "비밀번호" 면 비번찾기, 그 외 가입 인증)
    public String emailCheck(String MEmail, String check) {
        String uuid = "";
        String numberType = "";

        if (check.equals("비밀번호")) {
            numberType = "password";
        } else {
            numberType = "authentication number";
        }
        uuid = UUID.randomUUID().toString().substring(0, 8);

        MimeMessage mail = mailSender.createMimeMessage();
        String message = "<div style=\"background-color: #333; color: #FFF; width: calc(100% / 3); margin: 0 auto; padding: 32px; border-radius: 16px; box-shadow: 0 8px 16px rgba(0,0,0,0.2); text-align: center;\">" + "<h2 style=\"color: #FFD700; font-size: 36px; text-align: left;\">안녕하세요. DevHub 입니다.</h2>" + "<h3 style=\"color: #FFD700; font-size: 24px; text-align: left;\">Hi. We are DevHub</h3>" + "<p style=\"color: #FFF; font-size: 18px; text-align: left;\">" + check + "는 <strong style=\"color: #FFD700;\"> " + uuid + "</strong> 입니다.</p>" + "<p style=\"color: #FFF; font-size: 18px; text-align: left;\">The " + numberType + " is <strong style=\"color: #FFD700;\"> " + uuid + "</strong>.</p>" + "<img src=\"http://localhost:9091/images/robot.JPG\" alt=\"로봇 이미지\" style=\"width: 100%; height: auto; margin-top: 40px;\">" + "<br>" + "<br>" + "<p style=\"color: #FFF; font-size: 10px; text-align: right;\">자세한 문의 사항은 010-4135-4158로 문의바랍니다.</p>" + "<p style=\"color: #FFF; font-size: 10px; text-align: right;\">For more information, please contact 010-4135-4158.</p>" + "</div>";

        try {
            mail.setSubject("인천일보 아카데미 인증번호");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(MEmail));

            mailSender.send(mail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return uuid;
    }

    // 회원가입: 프로필 저장 → 비밀번호 암호화 → DB 저장
    public ModelAndView mJoin(MemberDTO member) {
        mav = new ModelAndView();

        MultipartFile MProfile = member.getMProfile();
        if (!MProfile.isEmpty()) {
            String uuid = UUID.randomUUID().toString().substring(0, 8);
            String originalFilename = MProfile.getOriginalFilename();
            String MProfileName = uuid + "_" + originalFilename;
            member.setMProfileName(MProfileName);

            String savePath = path + "\\" + MProfileName;
            try {
                MProfile.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        member.setMPw(pwEnc.encode(member.getMPw()));

        try {
            mrepo.save(MemberEntity.toEntity(member));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mav.setViewName("/member/login");

        return mav;
    }

    // 로그인: 비밀번호가 일치하면 세션에 사용자 정보 저장
    public ModelAndView mLogin(MemberDTO member) {
        mav = new ModelAndView();

        Optional<MemberEntity> entity = mrepo.findById(member.getMId());
        if (entity.isPresent()) {
            if (pwEnc.matches(member.getMPw(), entity.get().getMPw())) {
                MemberDTO login = MemberDTO.toDTO(entity.get());

                session.setAttribute("loginId", login.getMId());
                session.setAttribute("loginProfile", login.getMProfileName());
                session.setAttribute("loginMEmail", login.getMEmail());
                session.setAttribute("loginMPoint", login.getMPoint());
                session.setAttribute("loginName", login.getMName());
                session.setAttribute("loggedInUser", true);
            }
        }
        mav.setViewName("index");

        return mav;
    }

    // 회원 정보 수정: 기존 프로필 삭제 → 새 프로필 저장 → 비밀번호 재암호화 → 저장
    public ModelAndView mModify(MemberDTO member) {
        mav = new ModelAndView();

        MultipartFile MProfile = member.getMProfile();

        // 새 프로필 사진이 있을 때만 기존 사진을 지우고 교체 (사진 미변경 시 기존 사진 보존)
        if(!MProfile.isEmpty()) {
            if(member.getMProfileName() != null){
                File delFile = new File(path + "\\" + member.getMProfileName());
                if(delFile.exists()) {
                    delFile.delete();
                }
            }

            String uuid = UUID.randomUUID().toString().substring(0,8);
            String originalFilename = MProfile.getOriginalFilename();
            String MProfileName = uuid + "_" + originalFilename;

            member.setMProfileName(MProfileName);

            String savePath = path + "\\" + MProfileName;

            try {
                MProfile.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        member.setMPw(pwEnc.encode(member.getMPw()));

        // 보유 포인트는 폼의 hidden 값(위변조 가능)을 신뢰하지 않고 DB 기존 값을 유지
        mrepo.findById(member.getMId()).ifPresent(existing -> member.setMPoint(existing.getMPoint()));

        MemberEntity entity = MemberEntity.toEntity(member);

        try {
            mrepo.save(entity);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        mav.setViewName("redirect:/index");
        return mav;
    }

    // 회원 탈퇴: 연관 데이터(게시글·댓글·결제 등)를 먼저 모두 삭제한 뒤 회원 삭제 (FK cascade 미설정)
    public ModelAndView mDelete(MemberDTO member) {
        mav = new ModelAndView();

        if (member.getMProfileName() != null) {
            String delPath = path + "\\" + member.getMProfileName();

            File delFile = new File(delPath);
            if (delFile.exists()) {
                delFile.delete();
            }
        }

        List<ProductEntity> pentity = pdrepo.findByMember_MId(member.getMId());
        Optional<MemberEntity> mentity = mrepo.findById(member.getMId());
        if(mentity.isPresent()) {
            List<BoardEntity> bentity = brepo.findByMember_MId(member.getMId());
            for(BoardEntity b : bentity) {
                List<CodeEntity> centity = bcrepo.findByBoard_BNum(BoardDTO.toDTO(b).getBNum());
                for(CodeEntity c : centity) {
                    bcrepo.deleteById(CodeDTO.toDTO(c).getDTNum());
                }
            }
        }
        crepo.deleteByMember_MId(member.getMId());
        brepo.deleteByMember_MId(member.getMId());
        rrepo.deleteByMember_MId(member.getMId());
        pjrepo.deleteByMember_MId(member.getMId());
        trepo.deleteByMember_MId(member.getMId());
        pdrepo.deleteByMember_MId(member.getMId());
        pmrepo.deleteByMember_MId(member.getMId());
        hrepo.deleteByMember_MId(member.getMId());
        for(ProductEntity p : pentity) {
            cgrepo.deleteById(ProductDTO.toDTO(p).getPCId());
        }
        cartrepo.deleteByMember_MId(member.getMId());
        erepo.deleteByMember_MId(member.getMId());
        cprepo.deleteByMember_MId(member.getMId());
        lrepo.deleteByMember_MId(member.getMId());

        try {
            mrepo.deleteById(member.getMId());
            session.invalidate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mav.setViewName("redirect:/index");

        return mav;
    }

    // 비밀번호 재설정
    public void resetPassword(MemberDTO member) {
        Optional<MemberEntity> entity = mrepo.findById(member.getMId());

        if (entity.isPresent()) {
            MemberDTO memberDTO = MemberDTO.toDTO(entity.get());
            memberDTO.setMPw(pwEnc.encode(member.getMPw()));
            mrepo.save(MemberEntity.toEntity(memberDTO));
        }
    }

    // 전체 회원 목록
    public List<MemberDTO> memberList() {
        List<MemberEntity> entity = new ArrayList<>();
        List<MemberDTO> dto = new ArrayList<>();

        entity = mrepo.findAll();
        for (int i = 0; i < entity.size(); i++) {
            dto.add(MemberDTO.toDTO(entity.get(i)));
        }

        return dto;
    }

    // 회원정보 수정 폼 (상세보기 결과를 재사용)
    public ModelAndView modiForm(String MId) {
        mav = new ModelAndView();

        MemberDTO member = (MemberDTO) mView(MId).getModel().get("view");

        mav.setViewName("member/modify");
        mav.addObject("modify", member);

        return mav;
    }

    // 회원 탈퇴 폼
    public ModelAndView deleteForm(String MId) {
        mav = new ModelAndView();
        Optional<MemberEntity> entity = mrepo.findById(MId);
        if (entity.isPresent()) {
            MemberDTO member = MemberDTO.toDTO(entity.get());
            mav.addObject("delete", member);
            mav.setViewName("member/delete");
        } else {
            mav.setViewName("redirect:/index");
        }
        return mav;
    }

    // 회원 정보 상세보기
    public ModelAndView mView(String MId) {
        mav = new ModelAndView();
        Optional<MemberEntity> entity = mrepo.findById(MId);
        if (entity.isPresent()) {
            MemberDTO member = MemberDTO.toDTO(entity.get());
            mav.addObject("view", member);
            mav.setViewName("member/view");
        } else {
            mav.setViewName("redirect:/index");
        }
        return mav;
    }

    // 로그인 이력 저장 (현재 세션의 사용자와 연결)
    public void log(LoginDTO dto) {
        LoginEntity entity = LoginEntity.toEntity(dto);
        MemberEntity member = new MemberEntity();
        member.setMId((String)session.getAttribute("loginId"));
        entity.setMember(member);

        lrepo.save(entity);
    }

    // 특정 회원의 로그인 이력 목록 (최신순)
    public List<LoginDTO> getLoginHistoryByUserId(String userId) {
        List<LoginEntity> entityList = lrepo.findByMember_MIdOrderByLDateDesc(userId);
        List<LoginDTO> dtoList = new ArrayList<LoginDTO>();

        for (LoginEntity entity : entityList) {
            dtoList.add(LoginDTO.toDTO(entity));
        }
        return dtoList;
    }

}
