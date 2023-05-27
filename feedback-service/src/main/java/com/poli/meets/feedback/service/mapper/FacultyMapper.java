package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.Faculty;
import com.poli.meets.feedback.service.dto.FacultyDTO;
import com.poli.meets.feedback.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Faculty} and its DTO {@link FacultyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacultyMapper extends EntityMapper<FacultyDTO, Faculty> {


    @Mapping(target = "universityYears", ignore = true)
    @Mapping(target = "removeUniversityYears", ignore = true)
    Faculty toEntity(FacultyDTO facultyDTO);

    default Faculty fromId(Long id) {
        if (id == null) {
            return null;
        }
        Faculty faculty = new Faculty();
        faculty.setId(id);
        return faculty;
    }
}
