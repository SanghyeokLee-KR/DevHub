package com.icia.devhub.dto.order;

import lombok.Data;

@Data
public class ProductDTO {
    private int PId;
    private int PCId;
    private String PMId;
    private String PName;
    private int PPrice;
    private String PCategory;
    private String PExplain;

    public static ProductDTO toDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();
        dto.setPMId(entity.getMember().getMId());
        dto.setPId(entity.getPId());
        dto.setPCId(entity.getCategory().getCategory());
        dto.setPName(entity.getPName());
        dto.setPPrice(entity.getPPrice());
        dto.setPCategory(entity.getPCategory());
        dto.setPExplain(entity.getPExplain());

        return dto;
    }

}