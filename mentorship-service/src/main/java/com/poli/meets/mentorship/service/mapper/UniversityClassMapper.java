package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.UniversityClass;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.UniversityClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniversityClass} and its DTO {@link UniversityClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityYearMapper.class})
public interface UniversityClassMapper extends EntityMapper<UniversityClassDTO, UniversityClass> {

    @Mapping(source = "universityYear.id", target = "universityYearId")
    UniversityClassDTO toDto(UniversityClass universityClass);

    @Mapping(target = "mentorSubjects", ignore = true)
    @Mapping(target = "removeMentorSubjects", ignore = true)
    @Mapping(source = "universityYearId", target = "universityYear")
    UniversityClass toEntity(UniversityClassDTO universityClassDTO);

    default UniversityClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniversityClass universityClass = new UniversityClass();
        universityClass.setId(id);
        return universityClass;
    }
}