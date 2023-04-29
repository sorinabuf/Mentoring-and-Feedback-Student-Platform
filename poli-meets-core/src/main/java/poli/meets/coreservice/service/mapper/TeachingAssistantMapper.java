package poli.meets.coreservice.service.mapper;


import poli.meets.coreservice.domain.*;
import poli.meets.coreservice.service.dto.TeachingAssistantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeachingAssistant} and its DTO {@link TeachingAssistantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeachingAssistantMapper extends EntityMapper<TeachingAssistantDTO, TeachingAssistant> {


    @Mapping(target = "universityClasses", ignore = true)
    @Mapping(target = "removeUniversityClasses", ignore = true)
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
