package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.UniversityClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniversityClass} and its DTO {@link UniversityClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {TeacherMapper.class, UniversityYearMapper.class})
public interface UniversityClassMapper extends EntityMapper<UniversityClassDTO, UniversityClass> {

    UniversityClassDTO toDto(UniversityClass universityClass);

    @Mapping(target = "teachingAssistants", ignore = true)
    @Mapping(target = "removeTeachingAssistants", ignore = true)
    UniversityClass toEntity(UniversityClassDTO universityClassDTO);

    default UniversityClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniversityClass universityClass = new UniversityClass();
        universityClass.setId(id);
        return universityClass;
    }
}
