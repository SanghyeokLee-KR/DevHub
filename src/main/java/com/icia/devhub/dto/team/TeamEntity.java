package com.icia.devhub.dto.team;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "DEV_TEAM")
@SequenceGenerator(name = "TAM_SEQ_GENERATOR", sequenceName = "TAM_SEQ", allocationSize = 1)
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAM_SEQ_GENERATOR")
    private int TId;

    @Column(nullable = false, length = 100)
    private String TName;

    @Column(nullable = false, length = 50)
    private String TSalary;

    @Column(nullable = false, length = 10)
    private String TExperience;

    @Column(nullable = false, length = 20)
    private String TWorkType;

    @Column(nullable = false, length = 20)
    private String TContract;

    @Column(nullable = false, length = 20)
    private String TEducation;

    @Column(nullable = false, updatable = false, length = 10)
    private String TPayday;

    @Column(nullable = false, length = 100)
    private String TSkill;

    // 인원수
    @Column(nullable = false, length = 100)
    private String THCount;

    @Column(nullable = false, length = 100)
    private String TDuration;

    @OneToMany(mappedBy = "team")
    private List<ProjectEntity> projects;

    @ManyToOne
    @JoinColumn(name = "TMId", nullable = false)
    private MemberEntity member;

    public static TeamEntity toEntity(TeamDTO dto) {
        TeamEntity entity = new TeamEntity();
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMId(dto.getTMId());

        entity.setTId(dto.getTId());
        entity.setTName(dto.getTName());
        entity.setTSalary(dto.getTSalary());
        entity.setTExperience(dto.getTExperience());
        entity.setTWorkType(dto.getTWorkType());
        entity.setTContract(dto.getTContract());
        entity.setTEducation(dto.getTEducation());
        entity.setTPayday(dto.getTPayday());
        entity.setTSkill(dto.getTSkill());
        entity.setTHCount(dto.getTHCount());
        entity.setTDuration(dto.getTDuration());
        entity.setMember(memberEntity);

        return entity;
    }
}