package com.icia.devhub.dto.order;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "DEV_PRODUCT")
@SequenceGenerator(name="PRD_SEQ_GENERATOR", sequenceName="CTT_SEQ", allocationSize=1)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRD_SEQ_GENERATOR")
    private int PId;

    @Column(nullable = false, length = 1000)
    private String PName;

    @Column(nullable = false)
    private int PPrice;

    @Column(nullable = false, length = 100)
    private String PCategory;

    @Column(nullable = false, length = 100000)
    private String PExplain;

    @ManyToOne
    @JoinColumn(name = "PCId")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product")
    private List<PaymentEntity> payments;

    @OneToMany(mappedBy = "product")
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "product")
    private List<HistoryEntity> historys;

    @ManyToOne
    @JoinColumn(name="PMId")
    private MemberEntity member;


    public static ProductEntity toEntity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(dto.getPCId());
        MemberEntity member = new MemberEntity();
        member.setMId(dto.getPMId());

        entity.setPId(dto.getPId());
        entity.setCategory(categoryEntity);
        entity.setPName(dto.getPName());
        entity.setPPrice(dto.getPPrice());
        entity.setPCategory(dto.getPCategory());
        entity.setPExplain(dto.getPExplain());
        entity.setMember(member);
        return entity;
    }
}