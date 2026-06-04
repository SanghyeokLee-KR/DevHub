package com.icia.devhub.dto.team;

import lombok.Data;

@Data
public class ResumeDTO {
    private int RId;
    private int RPId;
    private String RMId;
    private String RTitle;
    private String RExperience;
    private String REducation;
    private String RSkill;
    private String RGProcess;       // 성장과정
    private String RPersonality;    // 성격 및 생활신조
    private String RAD;             // 장점 및 단점
    private String RMotive;         // 지원동기
    private String RAspiration;     // 회사 업무에 대한 자세 및 포부

    public static ResumeDTO toDTO(ResumeEntity entity) {
        ResumeDTO dto = new ResumeDTO();

        dto.setRId(entity.getRId());
        dto.setRPId(entity.getProject().getPId());
        dto.setRMId(entity.getMember().getMId());
        dto.setRTitle(entity.getRTitle());
        dto.setRExperience(entity.getRExperience());
        dto.setREducation(entity.getREducation());
        dto.setRSkill(entity.getRSkill());
        dto.setRGProcess(entity.getRGProcess());
        dto.setRPersonality(entity.getRPersonality());
        dto.setRAD(entity.getRAD());
        dto.setRMotive(entity.getRMotive());
        dto.setRAspiration(entity.getRAspiration());

        return dto;
    }
}