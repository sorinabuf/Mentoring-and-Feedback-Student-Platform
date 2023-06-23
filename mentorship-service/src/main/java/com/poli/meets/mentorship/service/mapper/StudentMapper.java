package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.StudentDTO;

import com.poli.meets.mentorship.service.dto.StudentPostDTO;
import com.poli.meets.mentorship.service.util.ImageUtility;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityYearMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "image", qualifiedByName = "decompressImage", target = "image")
    StudentDTO toDto(Student student);

    Student toEntity(StudentPostDTO studentDTO);

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

    @Mapping(target = "mentors", ignore = true)
    @Mapping(target = "removeMentors", ignore = true)
    @Mapping(target = "meetingRequests", ignore = true)
    @Mapping(target = "removeMeetingRequests", ignore = true)
    @Mapping(source = "image", qualifiedByName = "compressImage", target = "image")
    Student toEntity(StudentDTO studentDTO);

    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "image", qualifiedByName = "compressImage", target = "image")
    Student toEntity(poli.meets.coreservice.service.dto.StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
