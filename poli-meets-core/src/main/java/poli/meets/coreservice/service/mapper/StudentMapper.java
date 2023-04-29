package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityYearMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "universityYear.id", target = "universityYearId")
    StudentDTO toDto(Student student);

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
