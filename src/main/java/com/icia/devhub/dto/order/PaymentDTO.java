package com.icia.devhub.dto.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private int PId;
    private int PPId;
    private String PMId;
    private LocalDateTime PDate;

    public static PaymentDTO toDTO(PaymentEntity entity) {
        PaymentDTO dto = new PaymentDTO();

        dto.setPId(entity.getPId());
        dto.setPPId(entity.getProduct().getPId());
        dto.setPMId(entity.getMember().getMId());
        dto.setPDate(entity.getPDate());

        return dto;
    }
}