package com.icia.devhub.dto.member;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginDTO {
    private int LId;
    private String LMId;
    private LocalDateTime LDate;
    private String IPAddr;
    private String location;
    private String isp;

    public static LoginDTO toDTO(LoginEntity entity) {
        LoginDTO dto = new LoginDTO();
        dto.setLId(entity.getLId());
        dto.setLMId(entity.getMember().getMId());
        dto.setLDate(entity.getLDate());
        dto.setIPAddr(entity.getIPAddr());
        dto.setLocation(entity.getLocation());
        dto.setIsp(entity.getIsp());
        return dto;
    }
}
