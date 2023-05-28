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

    @Mapping(source = "image", qualifiedByName = "decompressImage", target = "image")
    StudentDTO toDto(Student student);

    @Mapping(source = "image", qualifiedByName = "compressImage", target = "image")
    Student toEntity(StudentDTO studentDTO);

    @Named("decompressImage")
    default byte[] decompressImage(byte[] image) {
        if (image != null) {
            return ImageUtility.decompressImage(image);
        } else {
            return null;
        }
    }

    @Named("compressImage")
    default byte[] compressImage(byte[] image) {
        if (image != null) {
            return ImageUtility.compressImage(image);
        } else {
            return null;
        }
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
