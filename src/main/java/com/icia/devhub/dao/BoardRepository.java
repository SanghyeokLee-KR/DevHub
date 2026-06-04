package com.icia.devhub.dao;

import com.icia.devhub.dto.board.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    List<BoardEntity> findAllByOrderByBNumDesc();

    // 검색: 작성자 ID / 제목 / 내용
    List<BoardEntity> findByMember_MIdContainingOrderByBNumDesc(String keyword);

    List<BoardEntity> findByBTitleContainingOrderByBNumDesc(String keyword);

    List<BoardEntity> findByBContentContainingOrderByBNumDesc(String keyword);

    // 조회수 1 증가
    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity B SET B.BHit = B.BHit + 1 WHERE B.BNum = :BNum")
    void increaseBHit(@Param("BNum") int BNum);

    List<BoardEntity> findAllByMember_MIdContainingOrderByBNumDesc(String loginId);

    List<BoardEntity> findByMember_MId(String mId);

    void deleteByMember_MId(String mId);
}
