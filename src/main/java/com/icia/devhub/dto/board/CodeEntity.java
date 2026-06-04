package com.icia.devhub.dto.board;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="DEV_CODE")
@SequenceGenerator(name="DEVB_SEQ_GENERATOR", sequenceName="DEVB_SEQ", allocationSize=1)
public class CodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEVB_SEQ_GENERATOR")
    private int DTNum;

    @ManyToOne
    @JoinColumn(name = "DDNum")
    private BoardEntity board;

    @Column(nullable = false, length = 100)
    private String DName;

    @Column(nullable = false, length = 10000)
    private String DCode;

    public static CodeEntity toEntity(CodeDTO dto) {
        CodeEntity entity = new CodeEntity();
        BoardEntity board = new BoardEntity();
        board.setBNum(dto.getDDNum());

        entity.setDTNum(dto.getDTNum());
        entity.setBoard(board);
        entity.setDName(dto.getDName());
        entity.setDCode(dto.getDCode());

        return entity;
    }
}
