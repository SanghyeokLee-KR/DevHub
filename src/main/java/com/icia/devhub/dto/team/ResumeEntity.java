package com.icia.devhub.dto.team;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DEV_RESUME")
@SequenceGenerator(name = "RSM_SEQ_GENERATOR", sequenceName = "PRJ_SEQ", allocationSize = 1)
public class ResumeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RSM_SEQ_GENERATOR")
    private int RId;

    @ManyToOne
    @JoinColumn(name = "RPId")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "RMId", nullable = false)
    private MemberEntity member;

    @Column(nullable = false, length = 50)
    private String RTitle;

    @Column(nullable = false, length = 50)
    private String RExperience;

    @Column(nullable = false, length = 50)
    private String REducation;

    @Column(nullable = false, length = 50)
    private String RSkill;

    // 성장과정
    @Column(nullable = false, length = 10000)
    private String RGProcess;

    // 성격 및 생활신조
    @Column(nullable = false, length = 10000)
    private String RPersonality;

    // 장점 및 단점
    @Column(nullable = false, length = 10000)
    private String RAD;

    // 지원동기
    @Column(nullable = false, length = 10000)
    private String RMotive;

    // 회사 업무에 대한 자세 및 포부
    @Column(nullable = false, length = 10000)
    private String RAspiration;

    public static ResumeEntity toEntity(ResumeDTO dto) {
        ResumeEntity entity = new ResumeEntity();
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMId(dto.getRMId());

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setPId(dto.getRPId());

        entity.setRId(dto.getRId());
        entity.setMember(memberEntity);
        entity.setProject(projectEntity);
        entity.setRTitle(dto.getRTitle());
        entity.setRExperience(dto.getRExperience());
        entity.setREducation(dto.getREducation());
        entity.setRSkill(dto.getRSkill());
        entity.setRGProcess(dto.getRGProcess());
        entity.setRPersonality(dto.getRPersonality());
        entity.setRAD(dto.getRAD());
        entity.setRMotive(dto.getRMotive());
        entity.setRAspiration(dto.getRAspiration());

        return entity;
    }
}