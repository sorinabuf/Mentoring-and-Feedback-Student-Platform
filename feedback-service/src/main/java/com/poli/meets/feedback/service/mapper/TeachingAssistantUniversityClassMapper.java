package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.TeachingAssistantUniversityClass;
import com.poli.meets.feedback.domain.*;
import com.poli.meets.feedback.service.dto.TeachingAssistantUniversityClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeachingAssistantUniversityClass} and its DTO {@link TeachingAssistantUniversityClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {TeachingAssistantMapper.class, UniversityClassMapper.class})
public interface TeachingAssistantUniversityClassMapper extends EntityMapper<TeachingAssistantUniversityClassDTO, TeachingAssistantUniversityClass> {

    @Mapping(source = "teachingAssistant.id", target = "teachingAssistantId")
    @Mapping(source = "universityClass.id", target = "universityClassId")
    TeachingAssistantUniversityClassDTO toDto(TeachingAssistantUniversityClass teachingAssistantUniversityClass);

    @Mapping(source = "teachingAssistantId", target = "teachingAssistant")
    @Mapping(source = "universityClassId", target = "universityClass")
    TeachingAssistantUniversityClass toEntity(TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO);

    default TeachingAssistantUniversityClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeachingAssistantUniversityClass teachingAssistantUniversityClass = new TeachingAssistantUniversityClass();
        teachingAssistantUniversityClass.setId(id);
        return teachingAssistantUniversityClass;
    }
}
