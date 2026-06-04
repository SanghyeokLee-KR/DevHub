package com.icia.devhub.dto.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDTO {
    private int HId;
    private int HPId;
    private String HMId;
    private LocalDateTime HDate;
    private int HDPoint;

    public static HistoryDTO toDTO(HistoryEntity entity) {
        HistoryDTO dto = new HistoryDTO();

        dto.setHId(entity.getHId());
        dto.setHPId(entity.getProduct().getPId());
        dto.setHMId(entity.getMember().getMId());
        dto.setHDate(entity.getHDate());
        dto.setHDPoint(entity.getHDPoint());

        return dto;
    }
}