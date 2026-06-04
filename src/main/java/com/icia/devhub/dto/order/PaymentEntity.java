package com.icia.devhub.dto.order;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DEV_PAYMENT")
@SequenceGenerator(name="PMT_SEQ_GENERATOR", sequenceName="PMT_SEQ", allocationSize=1)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PMT_SEQ_GENERATOR")
    private int PId;

    @ManyToOne
    @JoinColumn(name = "PPId")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "PMId")
    private MemberEntity member;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime PDate;

    public static PaymentEntity toEntity(PaymentDTO dto) {
        PaymentEntity entity = new PaymentEntity();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setPId(dto.getPPId());

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMId(dto.getPMId());

        entity.setPId(dto.getPId());
        entity.setProduct(productEntity);
        entity.setMember(memberEntity);
        entity.setPDate(dto.getPDate());

        return entity;
    }
}