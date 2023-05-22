package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.Mentor;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.MentorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mentor} and its DTO {@link MentorDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface MentorMapper extends EntityMapper<MentorDTO, Mentor> {

    @Mapping(source = "student.id", target = "studentId")
    MentorDTO toDto(Mentor mentor);

    @Mapping(target = "mentorSubjects", ignore = true)
    @Mapping(target = "removeMentorSubjects", ignore = true)
    @Mapping(target = "mentorSkills", ignore = true)
    @Mapping(target = "removeMentorSkills", ignore = true)
    @Mapping(target = "meetingSlots", ignore = true)
    @Mapping(target = "removeMeetingSlots", ignore = true)
    @Mapping(source = "studentId", target = "student")
    Mentor toEntity(MentorDTO mentorDTO);

    default Mentor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mentor mentor = new Mentor();
        mentor.setId(id);
        return mentor;
    }
}
