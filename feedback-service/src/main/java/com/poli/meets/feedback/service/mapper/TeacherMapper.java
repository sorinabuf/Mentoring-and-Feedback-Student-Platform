package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.Teacher;
import com.poli.meets.feedback.service.dto.TeacherDTO;
import com.poli.meets.feedback.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Teacher} and its DTO {@link TeacherDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {

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
