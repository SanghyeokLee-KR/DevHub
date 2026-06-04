package com.icia.devhub.config;

import com.icia.devhub.dao.*;
import com.icia.devhub.dto.board.BoardEntity;
import com.icia.devhub.dto.board.CodeEntity;
import com.icia.devhub.dto.board.CommentEntity;
import com.icia.devhub.dto.member.MemberEntity;
import com.icia.devhub.dto.team.ProjectEntity;
import com.icia.devhub.dto.team.TeamEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * H2 데모 프로파일(--spring.profiles.active=h2)에서만 동작하는 더미 데이터 시더.
 * 개발자 커뮤니티 컨셉에 맞춰 회원·게시글·코드·댓글·팀모집 데이터를 주입한다. (운영 Oracle 에는 영향 없음)
 * 데모 회원 비밀번호는 모두 1234.
 */
@Component
@Profile("h2")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CodeRepository codeRepository;
    private final CommentRepository commentRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (memberRepository.count() > 0) return; // 이미 데이터가 있으면 건너뜀

        // ===== 1) 데모 회원 (비밀번호 모두 1234) =====
        MemberEntity kim  = memberRepository.save(member("devkim",    "김성훈", "devkim@devhub.com",    "남", "0db8a289_97.JPG", 3200));
        MemberEntity lee  = memberRepository.save(member("codinglee", "이정우", "codinglee@devhub.com", "남", "2fc99734_97.JPG", 1500));
        MemberEntity park = memberRepository.save(member("backpark",  "박서연", "backpark@devhub.com",  "여", "39ea97c3_3.jpg",  4800));
        MemberEntity choi = memberRepository.save(member("frontchoi", "최민재", "frontchoi@devhub.com", "남", "abcecfe1_11.jpg",  900));

        // ===== 2) 게시판 글 (개발자 커뮤니티) =====
        BoardEntity b1 = boardRepository.save(board(park, "Java",
                "Spring Boot JPA N+1 문제, 실무에서 어떻게 해결하세요?",
                "지연로딩 연관관계를 조회할 때마다 추가 쿼리가 N번씩 나가서 고민입니다. fetch join, @EntityGraph, batch size 중 실무에서 주로 어떤 방식을 쓰시는지 궁금합니다. 아래는 제가 시도한 fetch join 예시입니다.", 142));
        BoardEntity b2 = boardRepository.save(board(kim, "기타",
                "신입 개발자 코딩테스트 준비 로드맵 (백준 → 프로그래머스 → SWEA)",
                "비전공으로 시작해 6개월간 코테 준비한 경험을 공유합니다. 처음엔 백준 실버부터 자료구조/완전탐색을 다지고, 그 다음 프로그래머스 Lv.2~3로 유형을 익혔어요. DP와 그래프는 따로 단권화 노트를 만든 게 큰 도움이 됐습니다.", 256));
        BoardEntity b3 = boardRepository.save(board(choi, "JavaScript",
                "React 상태관리 2024 - Redux vs Recoil vs Zustand 뭐 쓰세요?",
                "신규 프로젝트 상태관리 라이브러리를 고르는 중인데, 보일러플레이트가 적은 Zustand로 마음이 기웁니다. 규모가 커졌을 때 유지보수는 어떤지 실사용 후기가 궁금해요. 간단한 Zustand 스토어 예시 첨부합니다.", 98));
        BoardEntity b4 = boardRepository.save(board(lee, "기타",
                "Git 협업 전략: rebase vs merge, 우리 팀이 정착시킨 방법",
                "feature 브랜치는 rebase로 깔끔하게 정리하고, main 병합은 merge commit을 남기는 방식으로 합의했습니다. 커밋 히스토리가 선형으로 유지되면서도 병합 시점이 남아 추적이 편해졌어요.", 187));
        BoardEntity b5 = boardRepository.save(board(kim, "기타",
                "비전공자 부트캠프 6개월 회고 - 취업 성공 후기",
                "졸업 후 진로를 못 정하다 부트캠프에 들어갔고, 파이널 프로젝트로 개발자 커뮤니티(DevHub)를 만들었습니다. 끝나고 두 달 만에 합격했는데, 결국 프로젝트에서 '내가 뭘 고민했는지'를 말할 수 있느냐가 핵심이었어요.", 312));
        BoardEntity b6 = boardRepository.save(board(park, "Java",
                "JWT vs 세션 인증, 실무에선 뭘 더 많이 쓰나요?",
                "MSA나 모바일 연동이면 JWT, 전통적인 서버 렌더링이면 세션이라고들 하는데 경계가 애매합니다. 토큰 탈취/만료 처리 때문에 오히려 세션이 편하다는 분들도 계시더라고요. 다들 어떤 기준으로 선택하시나요?", 134));
        BoardEntity b7 = boardRepository.save(board(lee, "기타",
                "Docker 입문 - 이미지/컨테이너 개념 한 번에 정리",
                "이미지는 '실행에 필요한 것을 묶은 템플릿', 컨테이너는 '그 이미지를 실행한 인스턴스'로 이해하면 쉽습니다. 로컬 환경 안 꼬이게 DB를 컨테이너로 띄우는 것부터 시작하는 걸 추천해요.", 76));
        BoardEntity b8 = boardRepository.save(board(choi, "기타",
                "개발자 번아웃, 어떻게 극복하시나요?",
                "신규 기능 + 운영 이슈 + 코드 리뷰가 겹치니 금방 지치네요. 퇴근 후 의식적으로 코드에서 멀어지기, 작은 사이드로 흥미 되찾기 같은 게 도움이 됐는데 다른 분들 방법도 듣고 싶습니다.", 65));

        // ===== 3) 코드 스니펫 (코드 공유 글) =====
        codeRepository.save(code(b1, "Java", """
                @Query("select b from Board b join fetch b.member where b.id = :id")
                Optional<Board> findWithMember(@Param("id") Long id);
                // fetch join 으로 연관 엔티티를 한 번의 쿼리로 함께 조회 → N+1 제거"""));
        codeRepository.save(code(b3, "JavaScript", """
                import { create } from 'zustand';

                export const useCounter = create((set) => ({
                  count: 0,
                  increase: () => set((s) => ({ count: s.count + 1 })),
                  reset: () => set({ count: 0 }),
                }));"""));

        // ===== 4) 댓글 =====
        commentRepository.save(comment(b1, lee,  "저는 단건은 fetch join, 목록은 @BatchSize로 나눠 씁니다. 페이징 들어가면 fetch join이 오히려 독이 돼요."));
        commentRepository.save(comment(b1, kim,  "맞아요, 컬렉션 fetch join + 페이징은 메모리 페이징이라 위험합니다. @EntityGraph 추천!"));
        commentRepository.save(comment(b3, park, "규모 커져도 Zustand 충분히 버팁니다. 다만 셀렉터 분리 안 하면 리렌더링 터져요."));

        // ===== 5) 팀/프로젝트 모집 (개발자 팀 구인) =====
        TeamEntity t1 = teamRepository.save(team(kim, "DevHub 사이드 프로젝트팀", "협의 후 결정", "신입~3년", "재택근무", "프로젝트", "학력무관", "협의",
                "Java, Spring Boot, JPA, React", "4명", "2024-08-01 ~ 2024-10-31"));
        projectRepository.save(project(t1, kim, "개발자 커뮤니티 웹앱 같이 만드실 분 모집합니다!", "Back-and",
                "DevHub 같은 개발자 커뮤니티를 사이드로 발전시킬 백엔드 1명, 프론트 2명을 찾습니다. 주 1회 온라인 미팅, GitHub 협업 경험 있으면 좋아요.", kim.getMProfileName(), kim.getMEmail(), 88));

        TeamEntity t2 = teamRepository.save(team(choi, "AI 챗봇 토이프로젝트", "무급(포트폴리오)", "무관", "재택근무", "스터디", "학력무관", "-",
                "React, TypeScript, OpenAI API", "3명", "2024-09-01 ~ 2024-11-30"));
        projectRepository.save(project(t2, choi, "AI 챗봇 서비스 프론트엔드 함께 하실 분!", "Front-and",
                "OpenAI API를 붙인 챗봇 웹서비스를 만듭니다. UI/UX에 관심 있는 프론트 개발자 환영! 디자인 시스템부터 같이 잡아가요.", choi.getMProfileName(), choi.getMEmail(), 53));

        TeamEntity t3 = teamRepository.save(team(lee, "예비창업 스타트업 합류", "지분 + 추후 협의", "1~5년", "혼합근무", "정규직", "학력무관", "매월 25일",
                "Spring, MySQL, AWS, Docker", "2명", "2024-08-15 ~ 상시"));
        projectRepository.save(project(t3, lee, "초기 스타트업 풀스택 개발자 구합니다 (지분 협의)", "기타",
                "예비창업패키지 선정 팀으로, 서비스 0→1 단계를 함께할 풀스택 개발자를 찾습니다. 빠른 실행과 오너십을 중요하게 봅니다.", lee.getMProfileName(), lee.getMEmail(), 71));

        TeamEntity t4 = teamRepository.save(team(park, "공모전 준비 팀 (백엔드)", "상금 분배", "신입~2년", "재택근무", "프로젝트", "재학/졸업", "-",
                "Java, Spring Boot, Oracle", "3명", "2024-09-10 ~ 2024-10-20"));
        projectRepository.save(project(t4, park, "개발 공모전 같이 나가실 백엔드 팀원!", "Back-and",
                "공공데이터 활용 공모전 출품 목표입니다. REST API 설계 경험 있는 백엔드 1명 구해요. 수상 시 상금 균등 분배합니다.", park.getMProfileName(), park.getMEmail(), 42));
    }

    // ---------- helpers ----------
    private MemberEntity member(String id, String name, String email, String gender, String profile, int point) {
        MemberEntity m = new MemberEntity();
        m.setMId(id);
        m.setMPw(passwordEncoder.encode("1234"));
        m.setMName(name);
        m.setMBirth("1996-01-01");
        m.setMGender(gender);
        m.setMEmail(email);
        m.setMPhone("01012345678");
        m.setMProfileName(profile);
        m.setMPoint(point);
        return m;
    }

    private BoardEntity board(MemberEntity writer, String type, String title, String content, int hit) {
        BoardEntity b = new BoardEntity();
        b.setMember(writer);
        b.setBType(type);
        b.setBTitle(title);
        b.setBContent(content);
        b.setBHit(hit);
        return b;
    }

    private CodeEntity code(BoardEntity board, String lang, String src) {
        CodeEntity c = new CodeEntity();
        c.setBoard(board);
        c.setDName(lang);
        c.setDCode(src);
        return c;
    }

    private CommentEntity comment(BoardEntity board, MemberEntity writer, String content) {
        CommentEntity c = new CommentEntity();
        c.setBoard(board);
        c.setMember(writer);
        c.setCContents(content);
        return c;
    }

    private TeamEntity team(MemberEntity writer, String name, String salary, String exp, String workType,
                            String contract, String edu, String payday, String skill, String hcount, String duration) {
        TeamEntity t = new TeamEntity();
        t.setMember(writer);
        t.setTName(name);
        t.setTSalary(salary);
        t.setTExperience(exp);
        t.setTWorkType(workType);
        t.setTContract(contract);
        t.setTEducation(edu);
        t.setTPayday(payday);
        t.setTSkill(skill);
        t.setTHCount(hcount);
        t.setTDuration(duration);
        return t;
    }

    private ProjectEntity project(TeamEntity team, MemberEntity writer, String pname, String ptype,
                                  String desc, String profile, String email, int hit) {
        ProjectEntity p = new ProjectEntity();
        p.setTeam(team);
        p.setMember(writer);
        p.setPName(pname);
        p.setPType(ptype);
        p.setPContact(desc);
        p.setPProfile(profile);
        p.setPEmail(email);
        p.setPHit(hit);
        p.setPDate(LocalDateTime.now());
        return p;
    }
}
