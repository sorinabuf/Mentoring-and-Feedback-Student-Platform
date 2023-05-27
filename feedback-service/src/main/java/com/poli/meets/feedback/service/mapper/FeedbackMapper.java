package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.Feedback;
import com.poli.meets.feedback.service.dto.FeedbackDTO;
import com.poli.meets.feedback.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feedback} and its DTO {@link FeedbackDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, TeachingAssistantMapper.class})
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, Feedback> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "universityClass.id", target = "universityClassId")
    @Mapping(source = "teachingAssistant.id", target = "teachingAssistantId")
    FeedbackDTO toDto(Feedback feedback);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "universityClassId", target = "universityClass.id")
    @Mapping(source = "teachingAssistantId", target = "teachingAssistant")
    Feedback toEntity(FeedbackDTO feedbackDTO);

    default Feedback fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setId(id);
        return feedback;
    }
}
