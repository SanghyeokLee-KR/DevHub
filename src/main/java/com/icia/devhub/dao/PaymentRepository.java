package com.icia.devhub.dao;

import com.icia.devhub.dto.order.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    void deleteByMember_MId(String mId);
}
