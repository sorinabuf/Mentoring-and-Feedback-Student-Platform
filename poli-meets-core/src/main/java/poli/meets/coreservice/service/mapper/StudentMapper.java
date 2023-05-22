package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.StudentDTO;

import org.mapstruct.*;
import poli.meets.coreservice.service.dto.StudentPostDTO;
import poli.meets.coreservice.service.util.ImageUtility;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityYearMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "image", qualifiedByName = "mapImage", target = "image")
    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);

    @Named("mapImage")
    default byte[] mapImage(byte[] image) {
        return ImageUtility.decompressImage(image);
    }

    Student toEntity(StudentPostDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
