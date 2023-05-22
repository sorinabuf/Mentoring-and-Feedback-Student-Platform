package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.MentorSkill;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link MentorSkill} entity.
 */
@Data
@NoArgsConstructor
public class MentorSkillDTO implements Serializable {
    
    private Long id;


    private Long mentorId;

    private Long skillId;
}
