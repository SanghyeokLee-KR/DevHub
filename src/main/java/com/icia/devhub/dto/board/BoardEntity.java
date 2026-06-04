package com.icia.devhub.dto.board;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="DEV_BOARD")
@SequenceGenerator(name="BTT_SEQ_GENERATOR", sequenceName="BTT_SEQ", allocationSize=1)
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BTT_SEQ_GENERATOR")
    private int BNum;

    @Column(nullable = false, length = 100)
    private String BTitle;

    @Column(nullable = false, length = 10000)
    private String BContent;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime BDate;

    @Column
    private int BHit;

    @Column
    private String BFileName;

    @Column
    private String BType;

    @ManyToOne
    @JoinColumn(name = "BWriter")
    private MemberEntity member;

    @OneToMany(mappedBy = "board")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "board")
    private List<CodeEntity> codes;

    // DTO -> Entity 변환
    public static BoardEntity toEntity(BoardDTO dto) {
        BoardEntity entity = new BoardEntity();
        MemberEntity member = new MemberEntity();
        member.setMId(dto.getBWriter());

        entity.setBNum(dto.getBNum());
        entity.setBTitle(dto.getBTitle());
        entity.setBContent(dto.getBContent());
        entity.setBDate(dto.getBDate());
        entity.setBHit(dto.getBHit());
        entity.setBFileName(dto.getBFileName());
        entity.setBType(dto.getBType());
        entity.setMember(member);

        return entity;
    }
}