package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.TeacherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Teacher} and its DTO {@link TeacherDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {


    @Mapping(target = "universityClasses", ignore = true)
    @Mapping(target = "removeUniversityClasses", ignore = true)
    Teacher toEntity(TeacherDTO teacherDTO);

    default Teacher fromId(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }
}
