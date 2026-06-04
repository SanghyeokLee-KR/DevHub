package com.icia.devhub.dto.coupon;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="DEV_COUPON")
@SequenceGenerator(name="CPN_SEQ_GENERATOR", sequenceName="CPN_SEQ", allocationSize=1)
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CPN_SEQ_GENERATOR")
    private int CId;

    @Column(nullable = false, length = 100)
    private String CName;

    @Column(nullable = false)
    private int CPoint;

    @ManyToOne
    @JoinColumn(name = "CMId")
    private MemberEntity member;

    public static CouponEntity toEntity(CouponDTO dto) {
        CouponEntity entity = new CouponEntity();
        MemberEntity member = new MemberEntity();
        member.setMId(dto.getCMId());

        entity.setCId(dto.getCId());
        entity.setMember(member);
        entity.setCName(dto.getCName());
        entity.setCPoint(dto.getCPoint());

        return entity;
    }
}