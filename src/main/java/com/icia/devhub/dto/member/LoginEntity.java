package com.icia.devhub.dto.member;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="DEV_LOGIN")
@SequenceGenerator(name="DLN_SEQ_GENERATOR", sequenceName="DLN_SEQ", allocationSize=1)
public class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DLN_SEQ_GENERATOR")
    private int LId;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime LDate;

    @Column(nullable = false, length = 100)
    private String IPAddr;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(nullable = false, length = 100)
    private String isp;

    @ManyToOne
    @JoinColumn(name = "LMId")
    private MemberEntity member;

    // DTO -> Entity 변환
    public static LoginEntity toEntity(LoginDTO dto) {
        LoginEntity entity = new LoginEntity();
        MemberEntity member = new MemberEntity();
        member.setMId(dto.getLMId());

        entity.setLId(dto.getLId());
        entity.setLDate(dto.getLDate());
        entity.setIPAddr(dto.getIPAddr());
        entity.setLocation(dto.getLocation());
        entity.setIsp(dto.getIsp());
        entity.setMember(member);

        return entity;
    }
}
