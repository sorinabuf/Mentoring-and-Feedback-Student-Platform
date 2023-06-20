package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.UniversityYear;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.UniversityYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniversityYear} and its DTO {@link UniversityYearDTO}.
 */
@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface UniversityYearMapper extends EntityMapper<UniversityYearDTO, UniversityYear> {

    @Mapping(source = "faculty.id", target = "facultyId")
    UniversityYearDTO toDto(UniversityYear universityYear);

    @Mapping(target = "universityClasses", ignore = true)
    @Mapping(target = "removeUniversityClasses", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "removeStudents", ignore = true)
    @Mapping(source = "facultyId", target = "faculty")
    UniversityYear toEntity(UniversityYearDTO universityYearDTO);

    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "id", ignore = true)
    UniversityYear toEntity(poli.meets.coreservice.service.dto.UniversityYearDTO studentDTO);

    default UniversityYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniversityYear universityYear = new UniversityYear();
        universityYear.setId(id);
        return universityYear;
    }
}
