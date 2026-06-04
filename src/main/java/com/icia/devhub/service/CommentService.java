package com.icia.devhub.service;

import com.icia.devhub.dao.CommentRepository;
import com.icia.devhub.dto.board.CommentDTO;
import com.icia.devhub.dto.board.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository crepo;

    public List<CommentDTO> commentList(int CBNum) {
        List<CommentDTO> dtoList = new ArrayList<>();
        List<CommentEntity> entityList = crepo.findAllByBoard_BNum(CBNum);

        for(CommentEntity entity : entityList) {
            dtoList.add(CommentDTO.toDTO(entity));
        }

        return dtoList;
    }

    public void commentWrite(CommentDTO comment) {
        crepo.save(CommentEntity.toEntity(comment));
    }

    // 기존 댓글 내용만 수정 (새 댓글이 생기지 않도록 CNum으로 조회 후 갱신)
    public void commentModify(CommentDTO comment) {
        CommentEntity entity = crepo.findById(comment.getCNum())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + comment.getCNum()));
        entity.setCContents(comment.getCContents());
        crepo.save(entity);
    }

    public void commentDelete(CommentDTO comment) {
        crepo.deleteById(CommentEntity.toEntity(comment).getCNum());
    }
}