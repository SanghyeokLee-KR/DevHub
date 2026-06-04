package com.icia.devhub.dao;

import com.icia.devhub.dto.team.ProjectEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    // 조회수 1 증가
    @Modifying
    @Transactional
    @Query("UPDATE ProjectEntity P SET P.PHit = P.PHit + 1 WHERE P.PId = :PId")
    void increaseHit(@Param("PId") int PId);

    List<ProjectEntity> findByTeam_TIdOrderByPIdDesc(int tId);

    List<ProjectEntity> findByPTypeContainingOrderByPIdDesc(String keyword);

    List<ProjectEntity> findByPContactContainingOrderByPIdDesc(String keyword);

    List<ProjectEntity> findByPNameContainingOrderByPIdDesc(String keyword);

    void deleteByMember_MId(String mId);
}
