package com.icia.devhub.controller;

import com.icia.devhub.service.OrderService;
import com.icia.devhub.dto.order.ProductDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService osvc;

    // 포인트 충전
    // TODO: 충전 포인트(MPoint)를 클라이언트 값으로 그대로 신뢰 중 — 실제 결제 서버검증(iamport) 필요
    @PostMapping("/charge")
    public ResponseEntity<Map<String, Object>> chargePoints(HttpSession session, @RequestBody Map<String, Object> payload) {
        String MId = (String) session.getAttribute("loginId");
        Map<String, Object> response = new HashMap<>();
        if (MId != null) {
            int MPoint = (int) payload.get("MPoint");
            String fromAddress = "hajincheol123@gmail.com";
            Map<String, Object> orderMap = (Map<String, Object>) payload.get("order");

            ProductDTO order = new ProductDTO();
            order.setPName((String) orderMap.get("PName"));
            order.setPPrice((int) orderMap.get("PPrice"));
            order.setPCategory((String) orderMap.get("PCategory"));
            order.setPExplain((String) orderMap.get("PExplain"));

            int points = osvc.chargePoints(MId, MPoint, session, order, fromAddress);
            if (points > 0) {
                response.put("message", "Points charged and email sent.");
                response.put("points", points);
                response.put("redirectUrl", "/");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Failed to charge points.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            response.put("message", "Invalid session or user not logged in.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 포인트 차감
    @PostMapping("/deductPoints")
    public ResponseEntity<Integer> deductPoints(HttpSession session, @RequestParam int MPoint) {
        String MId = (String) session.getAttribute("loginId");

        if (MId != null) {
            int points = osvc.deductPoints(MId, MPoint);
            return ResponseEntity.ok(points);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(0);
        }
    }

    // 현재 보유 포인트 조회
    @GetMapping("/getPoints")
    public ResponseEntity<Integer> getPoints(HttpSession session) {
        String MId = (String) session.getAttribute("loginId");
        if (MId != null) {
            int points = osvc.getMemberPoints(MId);
            return ResponseEntity.ok(points);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(0);
        }
    }

    // 상품(UI 소스) 구매 처리 후 안내 메일 발송
    @PostMapping("/getproduct")
    public ResponseEntity<Map<String, Object>> getproduct(HttpSession session, @RequestBody Map<String, Object> payload) {
        String loginId = (String) session.getAttribute("loginId");

        Map<String, Object> response = new HashMap<>();
        if (loginId != null) {
            String fromAddress = "hajincheol123@gmail.com";
            Map<String, Object> orderMap = (Map<String, Object>) payload.get("order");

            ProductDTO order = new ProductDTO();
            order.setPName((String) orderMap.get("PName"));
            order.setPPrice((int) orderMap.get("PPrice"));
            order.setPCategory((String) orderMap.get("PCategory"));
            order.setPExplain((String) orderMap.get("PExplain"));

            Integer productId = (Integer) orderMap.get("PId");

            if (productId == null) {
                response.put("message", "Product ID is missing");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            String UI = osvc.insertProduct(loginId, session, order, fromAddress);
            if (UI != null) {
                response.put("message", "email sent.");
                response.put("UI", UI);
                response.put("PId", productId);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Failed to purchase product.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            response.put("message", "Invalid session or user not logged in.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
