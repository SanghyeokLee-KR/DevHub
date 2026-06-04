package com.icia.devhub.service;

import com.icia.devhub.dao.CategoryRepository;
import com.icia.devhub.dao.MemberRepository;
import com.icia.devhub.dao.OrderRepository;
import com.icia.devhub.dto.member.MemberEntity;
import com.icia.devhub.dto.order.CategoryEntity;
import com.icia.devhub.dto.order.ProductDTO;
import com.icia.devhub.dto.order.ProductEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final MemberRepository mrepo;
    private final HttpSession session;
    private final OrderRepository orepo;
    private final CategoryRepository crepo;
    private final EmailService esvc;

    // 포인트 충전 + 구매 내역 저장 + 안내 메일 발송 (충전 후 잔액 반환, 회원 없으면 0)
    public int chargePoints(String MId, int MPoint, HttpSession session, ProductDTO order, String fromAddress) {
        Optional<MemberEntity> entity = mrepo.findById(MId);
        if (entity.isPresent()) {
            MemberEntity memberEntity = entity.get();
            int currentPoints = memberEntity.getMPoint();
            memberEntity.setMPoint(currentPoints + MPoint);
            mrepo.save(memberEntity);
            session.setAttribute("loginMPoint", memberEntity.getMPoint());

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryName(order.getPCategory());
            categoryEntity = crepo.save(categoryEntity);

            ProductEntity productEntity = ProductEntity.toEntity(order);
            productEntity.setMember(memberEntity);
            productEntity.setCategory(categoryEntity);
            ProductEntity savedEntity = orepo.save(productEntity);
            ProductDTO savedOrder = ProductDTO.toDTO(savedEntity);

            esvc.sendPurchaseDetails(fromAddress, session, savedOrder);

            return memberEntity.getMPoint();
        }
        return 0;
    }

    // 포인트 차감 (잔액이 충분할 때만 차감, 부족하면 0 반환)
    public int deductPoints(String MId, int MPoint) {
        if (MPoint <= 0) {  // 음수/0 차감 차단 (음수를 보내면 오히려 포인트가 증가하는 버그 방지)
            return 0;
        }
        Optional<MemberEntity> member = mrepo.findById(MId);
        if (member.isPresent()) {
            MemberEntity memberEntity = member.get();
            int currentPoints = memberEntity.getMPoint();
            if (currentPoints >= MPoint) {
                memberEntity.setMPoint(currentPoints - MPoint);
                mrepo.save(memberEntity);
                session.setAttribute("loginMPoint", memberEntity.getMPoint());
                return MPoint;
            }
        }
        return 0;
    }

    // 보유 포인트 조회
    public int getMemberPoints(String mId) {
        Optional<MemberEntity> entity = mrepo.findById(mId);
        if (entity.isPresent()) {
            return entity.get().getMPoint();
        } else {
            return 0;
        }
    }

    // 구매 내역 조회 (검색어가 있으면 설명·이름으로 필터)
    public List<ProductDTO> phistory(String PExplain, String PName, String MId) {
        Optional<MemberEntity> optionalMember = mrepo.findById(MId);
        if (optionalMember.isPresent()) {
            MemberEntity member = optionalMember.get();

            if (!mrepo.existsById(member.getMId())) {
                mrepo.save(member);
            }

            List<ProductEntity> phistory;
            if (PExplain.isEmpty() && PName.isEmpty()) {
                phistory = orepo.findAllByMember_MIdOrderByPId(MId);
            } else {
                phistory = orepo.findByMember_MIdAndPExplainAndPNameOrderByPIdAsc(MId, PExplain, PName);
            }

            List<ProductDTO> productDTOList = new ArrayList<>();
            for (ProductEntity product : phistory) {
                productDTOList.add(ProductDTO.toDTO(product));
            }
            return productDTOList;
        } else {
            return new ArrayList<>();
        }
    }

    // 상품 구매 내역 저장 + 안내 메일 발송 (회원 ID 반환, 회원 없으면 null)
    public String insertProduct(String loginId, HttpSession session, ProductDTO order, String fromAddress) {
        Optional<MemberEntity> entity = mrepo.findById(loginId);
        if (entity.isPresent()) {
            MemberEntity memberEntity = entity.get();
            mrepo.save(memberEntity);

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryName(order.getPCategory());
            categoryEntity = crepo.save(categoryEntity);

            ProductEntity productEntity = ProductEntity.toEntity(order);
            productEntity.setMember(memberEntity);
            productEntity.setCategory(categoryEntity);
            ProductEntity savedEntity = orepo.save(productEntity);

            esvc.sendPurchaseDetails(fromAddress, session, ProductDTO.toDTO(savedEntity));

            return memberEntity.getMId();
        }
        return null;
    }
}
