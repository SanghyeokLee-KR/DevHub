package com.icia.devhub.dto.team;

import lombok.Data;

@Data
public class TeamDTO {
    private int TId;
    private String TMId;
    private String TName;
    private String TSalary;
    private String TExperience;
    private String TWorkType;
    private String TContract;
    private String TEducation;
    private String TPayday;
    private String TSkill;
    private String THCount;     // 인원수
    private String TDuration;

    public static TeamDTO toDTO(TeamEntity entity) {
        TeamDTO dto = new TeamDTO();

        dto.setTId(entity.getTId());
        dto.setTMId(entity.getMember().getMId());
        dto.setTName(entity.getTName());
        dto.setTSalary(entity.getTSalary());
        dto.setTExperience(entity.getTExperience());
        dto.setTWorkType(entity.getTWorkType());
        dto.setTContract(entity.getTContract());
        dto.setTEducation(entity.getTEducation());
        dto.setTPayday(entity.getTPayday());
        dto.setTSkill(entity.getTSkill());
        dto.setTHCount(entity.getTHCount());
        dto.setTDuration(entity.getTDuration());

        return dto;
    }
}