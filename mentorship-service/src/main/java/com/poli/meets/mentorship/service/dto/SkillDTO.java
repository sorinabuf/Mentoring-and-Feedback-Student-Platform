package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.Skill;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link Skill} entity.
 */
@Data
@NoArgsConstructor
public class SkillDTO implements Serializable {
    
    private Long id;

    private String name;

}
