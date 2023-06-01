package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.domain.*;
import com.poli.meets.feedback.service.dto.FeedbackUniversityYearDTO;
import com.poli.meets.feedback.service.dto.UniversityYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniversityYear} and its DTO {@link UniversityYearDTO}.
 */
@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface UniversityYearMapper extends EntityMapper<UniversityYearDTO, UniversityYear> {

    @Mapping(source = "faculty.id", target = "facultyId")
    UniversityYearDTO toDto(UniversityYear universityYear);

    FeedbackUniversityYearDTO toFeedbackDto(UniversityYear universityYear);

    @Mapping(source = "facultyId", target = "faculty")
    UniversityYear toEntity(UniversityYearDTO universityYearDTO);

    default UniversityYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniversityYear universityYear = new UniversityYear();
        universityYear.setId(id);
        return universityYear;
    }
}
