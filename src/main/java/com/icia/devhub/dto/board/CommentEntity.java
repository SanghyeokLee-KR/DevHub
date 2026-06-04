package com.icia.devhub.dto.board;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DEV_COMMENT")
@SequenceGenerator(name="CTT_SEQ_GENERATOR", sequenceName="CTT_SEQ", allocationSize=1)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CTT_SEQ_GENERATOR")
    private int CNum;

    @ManyToOne
    @JoinColumn(name = "CBNum")
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name = "CWriter")
    private MemberEntity member;

    @Column(nullable = false, length = 10000)
    private String CContents;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime CDate;

    public static CommentEntity toEntity(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        MemberEntity member = new MemberEntity();
        BoardEntity board = new BoardEntity();
        member.setMId(dto.getCWriter());
        board.setBNum(dto.getCBNum());

        entity.setCNum(dto.getCNum());
        entity.setBoard(board);
        entity.setMember(member);
        entity.setCContents(dto.getCContents());
        entity.setCDate(dto.getCDate());

        return entity;
    }
}