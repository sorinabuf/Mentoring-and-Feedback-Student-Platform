package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.UniversityYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniversityYear} and its DTO {@link UniversityYearDTO}.
 */
@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface UniversityYearMapper extends EntityMapper<UniversityYearDTO, UniversityYear> {


    UniversityYearDTO toDto(UniversityYear universityYear);

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
