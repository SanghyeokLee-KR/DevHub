package com.icia.devhub.dto.board;

import lombok.Data;

@Data
public class CodeDTO {
    private int DTNum;
    private int DDNum;
    private String DName;
    private String DCode;

    public static CodeDTO toDTO(CodeEntity entity) {
        CodeDTO dto = new CodeDTO();

        dto.setDTNum(entity.getDTNum());
        dto.setDDNum(entity.getBoard().getBNum());
        dto.setDName(entity.getDName());
        dto.setDCode(entity.getDCode());

        return dto;
    }
}