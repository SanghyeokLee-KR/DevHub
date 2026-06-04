package com.icia.devhub.dao;

import com.icia.devhub.dto.coupon.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {
    void deleteByMember_MId(String mId);
}
