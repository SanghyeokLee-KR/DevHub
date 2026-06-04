package com.icia.devhub.dao;

import com.icia.devhub.dto.order.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {

    List<HistoryEntity> findByMember_MId(String mid);

    void deleteByMember_MId(String mId);
}
