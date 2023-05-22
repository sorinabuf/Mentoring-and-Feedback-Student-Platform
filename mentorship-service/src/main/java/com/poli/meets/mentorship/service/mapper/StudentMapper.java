package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityYearMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "universityYear.id", target = "universityYearId")
    StudentDTO toDto(Student student);

    @Mapping(target = "mentors", ignore = true)
    @Mapping(target = "removeMentors", ignore = true)
    @Mapping(target = "meetingRequests", ignore = true)
    @Mapping(target = "removeMeetingRequests", ignore = true)
    @Mapping(source = "universityYearId", target = "universityYear")
    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
