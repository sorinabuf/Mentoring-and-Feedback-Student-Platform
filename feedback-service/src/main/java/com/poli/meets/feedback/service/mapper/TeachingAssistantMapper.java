package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.TeachingAssistant;
import com.poli.meets.feedback.service.dto.TeachingAssistantDTO;
import com.poli.meets.feedback.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeachingAssistant} and its DTO {@link TeachingAssistantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeachingAssistantMapper extends EntityMapper<TeachingAssistantDTO, TeachingAssistant> {


    @Mapping(target = "universityClasses", ignore = true)
    @Mapping(target = "removeUniversityClasses", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "removeFeedbacks", ignore = true)
    TeachingAssistant toEntity(TeachingAssistantDTO teachingAssistantDTO);

    default TeachingAssistant fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeachingAssistant teachingAssistant = new TeachingAssistant();
        teachingAssistant.setId(id);
        return teachingAssistant;
    }
}
