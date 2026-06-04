package com.icia.devhub.dto.order;

import com.icia.devhub.dto.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DEV_HISTORY")
@SequenceGenerator(name = "DHT_SEQ_GENERATOR", sequenceName = "DHT_SEQ", allocationSize = 1)
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DHT_SEQ_GENERATOR")
    private int HId;

    @ManyToOne
    @JoinColumn(name = "HPId")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "HMId")
    private MemberEntity member;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime HDate;

    @Column(nullable = false)
    private int HDPoint;

    public static HistoryEntity toEntity(HistoryDTO dto) {
        HistoryEntity history = new HistoryEntity();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setPId(dto.getHPId());

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMId(dto.getHMId());

        history.setHId(dto.getHId());
        history.setProduct(productEntity);
        history.setMember(memberEntity);
        history.setHDate(dto.getHDate());
        history.setHDPoint(dto.getHDPoint());

        return history;
    }
}