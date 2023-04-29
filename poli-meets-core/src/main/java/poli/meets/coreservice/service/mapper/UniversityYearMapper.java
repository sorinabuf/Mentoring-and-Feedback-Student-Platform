package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.UniversityYearDTO;

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

    default UniversityYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniversityYear universityYear = new UniversityYear();
        universityYear.setId(id);
        return universityYear;
    }
}
