package com.icia.devhub.dto.team;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectDTO {
    private int PId;
    private int PTId;
    private String PName;
    private String PMWriter;
    private String PContact;
    private LocalDateTime PDate;
    private int PHit;
    private String PType;
    private String PProfile;
    private String PEmail;

    public static ProjectDTO toDTO(ProjectEntity entity) {
        ProjectDTO dto = new ProjectDTO();

        dto.setPId(entity.getPId());
        dto.setPTId(entity.getTeam().getTId());
        dto.setPMWriter(entity.getMember().getMId());
        dto.setPContact(entity.getPContact());
        dto.setPName(entity.getPName());
        dto.setPDate(entity.getPDate());
        dto.setPHit(entity.getPHit());
        dto.setPType(entity.getPType());
        dto.setPProfile(entity.getPProfile());
        dto.setPEmail(entity.getPEmail());

        return dto;
    }
}