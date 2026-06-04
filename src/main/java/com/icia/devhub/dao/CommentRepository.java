package com.icia.devhub.dao;

import com.icia.devhub.dto.board.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByBoard_BNum(int cbNum);

    void deleteByMember_MId(String mId);
}
