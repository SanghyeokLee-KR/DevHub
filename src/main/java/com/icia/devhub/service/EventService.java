package com.icia.devhub.service;

import com.icia.devhub.dao.EventRepository;
import com.icia.devhub.dao.MemberRepository;
import com.icia.devhub.dto.event.EventDTO;
import com.icia.devhub.dto.event.EventEntity;
import com.icia.devhub.dto.member.MemberEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final HttpSession session;
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    // 출석 이벤트 저장 및 회원 포인트 적립
    public void event(EventDTO dto) {
        String loginId = (String) session.getAttribute("loginId");

        MemberEntity member = memberRepository.findById(loginId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        // 같은 날짜에 이미 출석했으면 포인트 중복 적립 방지
        boolean alreadyAttended = eventRepository.findByMember_MId(loginId).stream()
                .anyMatch(e -> dto.getIDATE() != null && dto.getIDATE().equals(e.getIDATE()));
        if (alreadyAttended) {
            return;
        }

        // 적립 포인트는 서버가 결정 (클라이언트가 보낸 POINTS는 신뢰하지 않음): 일요일 100, 그 외 10
        int awardedPoints = (java.time.LocalDate.now().getDayOfWeek() == java.time.DayOfWeek.SUNDAY) ? 100 : 10;

        EventEntity entity = EventEntity.toEntity(dto);
        entity.setMember(member);
        entity.setPOINTS(awardedPoints);
        eventRepository.save(entity);

        member.setMPoint(member.getMPoint() + awardedPoints);
        memberRepository.save(member);
    }

    public List<String> getAttendanceDays(String userId) {
        List<EventEntity> events = eventRepository.findByMember_MId(userId);
        return events.stream()
                .map(EventEntity::getIDATE)
                .collect(Collectors.toList());
    }
}
