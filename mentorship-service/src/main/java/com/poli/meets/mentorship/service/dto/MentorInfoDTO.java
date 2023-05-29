package com.poli.meets.mentorship.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class MentorInfoDTO implements Serializable {
    private Long id;

    private String description;

    private List<UniversityClassDTO> subjects;

    private List<SkillDTO> skills;
}
