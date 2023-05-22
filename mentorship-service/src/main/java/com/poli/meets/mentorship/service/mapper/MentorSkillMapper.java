package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.MentorSkill;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.MentorSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MentorSkill} and its DTO {@link MentorSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {MentorMapper.class, SkillMapper.class})
public interface MentorSkillMapper extends EntityMapper<MentorSkillDTO, MentorSkill> {

    @Mapping(source = "mentor.id", target = "mentorId")
    @Mapping(source = "skill.id", target = "skillId")
    MentorSkillDTO toDto(MentorSkill mentorSkill);

    @Mapping(source = "mentorId", target = "mentor")
    @Mapping(source = "skillId", target = "skill")
    MentorSkill toEntity(MentorSkillDTO mentorSkillDTO);

    default MentorSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        MentorSkill mentorSkill = new MentorSkill();
        mentorSkill.setId(id);
        return mentorSkill;
    }
}
