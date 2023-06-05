package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.Feedback;
import com.poli.meets.feedback.service.dto.FeedbackPostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feedback} and its DTO {@link FeedbackPostDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface FeedbackMapper extends EntityMapper<FeedbackPostDTO, Feedback> {

    @Mapping(source = "universityClass.id", target = "universityClassId")
    FeedbackPostDTO toDto(Feedback feedback);

    @Mapping(source = "universityClassId", target = "universityClass.id")
    @Mapping(source = "categoryId", target = "category.id")
    Feedback toEntity(FeedbackPostDTO feedbackPostDTO);

    default Feedback fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setId(id);
        return feedback;
    }
}
