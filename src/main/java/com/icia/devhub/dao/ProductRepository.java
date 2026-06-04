package com.icia.devhub.dao;

import com.icia.devhub.dto.order.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByMember_MId(String mId);

    void deleteByMember_MId(String mId);
}
