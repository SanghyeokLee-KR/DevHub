package com.icia.devhub.dto.coupon;

import lombok.Data;

@Data
public class CouponDTO {
    private int CId;
    private String CMId;
    private String CName;
    private int CPoint;

    public static CouponDTO toDTO(CouponEntity entity) {
        CouponDTO dto = new CouponDTO();

        dto.setCId(entity.getCId());
        dto.setCMId(entity.getMember().getMId());
        dto.setCName(entity.getCName());
        dto.setCPoint(entity.getCPoint());

        return dto;
    }
}