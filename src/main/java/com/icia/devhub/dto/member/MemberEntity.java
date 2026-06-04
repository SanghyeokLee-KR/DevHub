package com.icia.devhub.dto.member;

import com.icia.devhub.dto.board.BoardEntity;
import com.icia.devhub.dto.board.CommentEntity;
import com.icia.devhub.dto.order.CartEntity;
import com.icia.devhub.dto.order.HistoryEntity;
import com.icia.devhub.dto.order.PaymentEntity;
import com.icia.devhub.dto.team.ProjectEntity;
import com.icia.devhub.dto.team.ResumeEntity;
import com.icia.devhub.dto.team.TeamEntity;
import com.icia.devhub.dto.coupon.CouponEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="DEV_MEMBER")
public class MemberEntity {
    @Id
    @Column(nullable = false, length = 100)
    private String MId;

    @Column(nullable = false, length = 100)
    private String MPw;

    @Column(nullable = false, length = 5)
    private String MName;

    @Column(nullable = false, length = 100)
    private String MBirth;

    @Column(nullable = false, length = 5)
    private String MGender;

    @Column(nullable = false, length = 100)
    private String MEmail;

    @Column(nullable = false, length = 11)
    private String MPhone;

    @Column(nullable = false, length = 1000)
    private String MProfileName;

    @Column(nullable = false)
    private int MPoint;

    @OneToMany(mappedBy = "member")
    private List<BoardEntity> boards;

    @OneToMany(mappedBy = "member")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "member")
    private List<CouponEntity> coupons;

    @OneToMany(mappedBy = "member")
    private List<LoginEntity> logins;

    @OneToMany(mappedBy = "member")
    private List<PaymentEntity> payments;

    @OneToMany(mappedBy = "member")
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "member")
    private List<HistoryEntity> historys;

    @OneToMany(mappedBy = "member")
    private List<TeamEntity> teams;

    @OneToMany(mappedBy = "member")
    private List<ProjectEntity> projects;

    @OneToMany(mappedBy = "member")
    private List<ResumeEntity> resumes;

    // DTO -> Entity 변환
    public static MemberEntity toEntity(MemberDTO dto) {
        MemberEntity entity = new MemberEntity();

        entity.setMId(dto.getMId());
        entity.setMPw(dto.getMPw());
        entity.setMName(dto.getMName());
        entity.setMBirth(dto.getMBirth());
        entity.setMGender(dto.getMGender());
        entity.setMEmail(dto.getMEmail());
        entity.setMPhone(dto.getMPhone());
        entity.setMProfileName(dto.getMProfileName());
        entity.setMPoint(dto.getMPoint());

        return entity;
    }
}