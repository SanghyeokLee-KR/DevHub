package com.icia.devhub.dto.board;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private int CNum;
    private int CBNum;
    private String CWriter;
    private String CContents;
    private LocalDateTime CDate;

    public static CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();

        dto.setCNum(entity.getCNum());
        dto.setCBNum(entity.getBoard().getBNum());
        dto.setCWriter(entity.getMember().getMId());
        dto.setCContents(entity.getCContents());
        dto.setCDate(entity.getCDate());

        return dto;
    }
}