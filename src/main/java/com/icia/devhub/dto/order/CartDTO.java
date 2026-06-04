package com.icia.devhub.dto.order;

import lombok.Data;

@Data
public class CartDTO {
    private int CId;
    private String CMId;
    private int CPId;

    public static CartDTO toDTO(CartEntity entity) {
        CartDTO dto = new CartDTO();

        dto.setCId(entity.getCId());
        dto.setCMId(entity.getMember().getMId());
        dto.setCPId(entity.getProduct().getPId());

        return dto;
    }
}