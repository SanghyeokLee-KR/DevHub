package com.icia.devhub.dto.order;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DEV_CART")
@SequenceGenerator(name = "DCT_SEQ_GENERATOR", sequenceName = "DCT_SEQ", allocationSize = 1)
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DCT_SEQ_GENERATOR")
    private int CId;

    @ManyToOne
    @JoinColumn(name = "CMId")
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "CPId")
    private ProductEntity product;

    public static CartEntity toEntity(CartDTO dto) {
        CartEntity cart = new CartEntity();
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMId(dto.getCMId());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setPId(dto.getCPId());

        cart.setCId(dto.getCId());
        cart.setMember(memberEntity);
        cart.setProduct(productEntity);

        return cart;
    }
}