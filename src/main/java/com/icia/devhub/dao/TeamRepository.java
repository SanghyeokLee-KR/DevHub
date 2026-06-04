package com.icia.devhub.dao;

import com.icia.devhub.dto.team.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
    List<TeamEntity> findByTExperienceContainingOrderByTIdDesc(String keyword);

    List<TeamEntity> findByTContractContainingOrderByTIdDesc(String keyword);

    List<TeamEntity> findByTEducationContainingOrderByTIdDesc(String keyword);

    List<TeamEntity> findByTSkillContainingOrderByTIdDesc(String keyword);

    void deleteByMember_MId(String mId);
}