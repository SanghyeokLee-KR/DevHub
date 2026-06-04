package com.icia.devhub.dto.team;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "DEV_PROJECT")
@SequenceGenerator(name = "PRJ_SEQ_GENERATOR", sequenceName = "PRJ_SEQ", allocationSize = 1)
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRJ_SEQ_GENERATOR")
    private int PId;

    @ManyToOne
    @JoinColumn(name = "PTId", nullable = false)
    private TeamEntity team;

    @Column(nullable = false, length = 100)
    private String PName;

    @ManyToOne
    @JoinColumn(name = "PMWriter", nullable = false)
    private MemberEntity member;

    @Column(updatable = false)
    private LocalDateTime PDate;

    @Column(nullable = false)
    private int PHit;

    @Column(nullable = false, length = 1000)
    private String PContact;

    @Column(nullable = false, length = 20)
    private String PType;

    @Column(nullable = false, length = 30)
    private String PProfile;

    @Column(nullable = false, length = 30)
    private String PEmail;

    @OneToMany(mappedBy = "project")
    private List<ResumeEntity> resumes;

    public static ProjectEntity toEntity(ProjectDTO dto) {
        ProjectEntity entity = new ProjectEntity();
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTId(dto.getPTId());

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMId(dto.getPMWriter());

        entity.setPId(dto.getPId());
        entity.setMember(memberEntity);
        entity.setTeam(teamEntity);
        entity.setPName(dto.getPName());
        entity.setPType(dto.getPType());
        entity.setPContact(dto.getPContact());
        entity.setPHit(dto.getPHit());
        entity.setPDate(dto.getPDate());
        entity.setPProfile(dto.getPProfile());
        entity.setPEmail(dto.getPEmail());

        return entity;
    }
}