package com.icia.devhub.dao;

import com.icia.devhub.dto.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {
    List<EventEntity> findByMember_MId(String memberId);

    void deleteByMember_MId(String mId);
}
