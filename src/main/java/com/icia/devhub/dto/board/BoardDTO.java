package com.icia.devhub.dto.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private int BNum;
    private String BWriter;
    private String BTitle;
    private String BContent;
    private LocalDateTime BDate;
    private int BHit;
    private MultipartFile BFile;
    private String BFileName;
    private String BType;

    // Entity -> DTO 변환
    public static BoardDTO toDTO(BoardEntity entity) {
        BoardDTO dto = new BoardDTO();

        dto.setBNum(entity.getBNum());
        dto.setBWriter(entity.getMember().getMId());
        dto.setBTitle(entity.getBTitle());
        dto.setBContent(entity.getBContent());
        dto.setBDate(entity.getBDate());
        dto.setBHit(entity.getBHit());
        dto.setBFileName(entity.getBFileName());
        dto.setBType(entity.getBType());

        return dto;
    }
}