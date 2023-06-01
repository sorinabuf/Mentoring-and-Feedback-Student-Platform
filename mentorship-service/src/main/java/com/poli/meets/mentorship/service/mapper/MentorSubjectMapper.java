package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.MentorSubject;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.MentorSubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MentorSubject} and its DTO {@link MentorSubjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityClassMapper.class, MentorMapper.class})
public interface MentorSubjectMapper extends EntityMapper<MentorSubjectDTO, MentorSubject> {

    @Mapping(source = "mentor.id", target = "mentorId")
    MentorSubjectDTO toDto(MentorSubject mentorSubject);

    @Mapping(source = "mentorId", target = "mentor")
    MentorSubject toEntity(MentorSubjectDTO mentorSubjectDTO);

    default MentorSubject fromId(Long id) {
        if (id == null) {
            return null;
        }
        MentorSubject mentorSubject = new MentorSubject();
        mentorSubject.setId(id);
        return mentorSubject;
    }
}
