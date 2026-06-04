package com.icia.devhub.service;

import com.icia.devhub.dao.HistoryRepository;
import com.icia.devhub.dao.MemberRepository;
import com.icia.devhub.dao.OrderRepository;
import com.icia.devhub.dto.member.MemberEntity;
import com.icia.devhub.dto.order.HistoryDTO;
import com.icia.devhub.dto.order.HistoryEntity;
import com.icia.devhub.dto.order.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryService {

    private final MemberRepository mrepo;
    private final HistoryRepository hrepo;
    private final OrderRepository orepo;




    // 열람 내역 조회
    public List<HistoryDTO> getHistory(String mid) {
        List<HistoryEntity> historyEntities = hrepo.findByMember_MId(mid);
        return historyEntities.stream()
                .map(entity -> {
                    HistoryDTO dto = new HistoryDTO();
                    dto.setHId(entity.getHId());
                    dto.setHMId(entity.getMember().getMId());
                    dto.setHDate(entity.getHDate());
                    dto.setHDPoint(entity.getHDPoint());
                    if (entity.getProduct() != null) {
                        dto.setHPId(entity.getProduct().getPId());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 열람 내역 생성 (구매한 제품 목록을 회원의 히스토리로 저장)
    public void saveHistory(String mid, List<Integer> pids) {
        MemberEntity member = mrepo.findById(mid).orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + mid));
        for (Integer pid : pids) {
            ProductEntity product = orepo.findByPId(pid);
            if (product == null) {
                continue;
            }
            HistoryEntity history = new HistoryEntity();
            history.setMember(member);
            history.setProduct(product);
            history.setHDate(LocalDateTime.now());
            history.setHDPoint(200);  // 포인트는 예시로 200으로 설정

            hrepo.save(history);
        }
    }
}
