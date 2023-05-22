package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.Skill;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.SkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {


    @Mapping(target = "mentorSkills", ignore = true)
    @Mapping(target = "removeMentorSkills", ignore = true)
    Skill toEntity(SkillDTO skillDTO);

    default Skill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(id);
        return skill;
    }
}
