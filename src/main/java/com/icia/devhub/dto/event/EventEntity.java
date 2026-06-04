package com.icia.devhub.dto.event;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DEV_Event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IId;

    @Column(nullable = false, length = 100)
    private String IDATE;

    @Column(nullable = false)
    private int POINTS;

    // 포인트 획득 시각 (저장 시 자동 기록)
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime PTIME;

    @ManyToOne
    @JoinColumn(name = "IMID")
    private MemberEntity member;

    public static EventEntity toEntity(EventDTO dto) {
        EventEntity entity = new EventEntity();
        MemberEntity member = new MemberEntity();

        entity.setIId(dto.getIId());
        entity.setIDATE(dto.getIDATE());
        entity.setPOINTS(dto.getPOINTS());
        entity.setPTIME(dto.getPTIME());
        entity.setMember(member);

        return entity;
    }
}
