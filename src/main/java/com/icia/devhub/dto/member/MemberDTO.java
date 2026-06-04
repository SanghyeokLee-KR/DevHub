package com.icia.devhub.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberDTO {
    private String MId;
    @JsonIgnore // 비밀번호 해시는 JSON 응답(/memberList 등)에 절대 노출하지 않음
    private String MPw;
    private String MName;
    private String MBirth;
    private String MGender;
    private String MEmail;
    private String MPhone;

    private MultipartFile MProfile;
    private String MProfileName;

    private int MPoint;

    // Entity -> DTO 변환
    public static MemberDTO toDTO(MemberEntity entity) {
        MemberDTO dto = new MemberDTO();

        dto.setMId(entity.getMId());
        dto.setMPw(entity.getMPw());
        dto.setMName(entity.getMName());
        dto.setMBirth(entity.getMBirth());
        dto.setMGender(entity.getMGender());
        dto.setMEmail(entity.getMEmail());
        dto.setMPhone(entity.getMPhone());
        dto.setMProfileName(entity.getMProfileName());
        dto.setMPoint(entity.getMPoint());

        return dto;
    }
}