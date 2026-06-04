package com.icia.devhub.dao;

import com.icia.devhub.dto.member.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {

        List<LoginEntity> findByMember_MIdOrderByLDateDesc(String userId);

    void deleteByMember_MId(String mId);
}
