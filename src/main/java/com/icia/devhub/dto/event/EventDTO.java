package com.icia.devhub.dto.event;

import com.icia.devhub.dto.order.CartDTO;
import com.icia.devhub.dto.order.CartEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class EventDTO {
    private int IId;
    private String IDATE;
    private int POINTS;
    private LocalDateTime PTIME;
    private String IMID;

    public static EventDTO toDTO(EventEntity entity) {
        EventDTO dto = new EventDTO();

        dto.setIId(entity.getIId());
        dto.setIDATE(entity.getIDATE());
        dto.setPOINTS(entity.getPOINTS());
        dto.setPTIME(entity.getPTIME());
        dto.setIMID(entity.getMember().getMId());

        return dto;
    }
}