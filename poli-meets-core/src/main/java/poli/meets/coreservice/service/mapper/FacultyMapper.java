package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.FacultyDTO;

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
