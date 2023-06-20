package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.UniversityClass;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.service.dto.UniversityClassDTO;

import com.poli.meets.mentorship.service.dto.UniversityClassMentorshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniversityClass} and its DTO {@link UniversityClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityYearMapper.class})
public interface UniversityClassMapper extends EntityMapper<UniversityClassDTO, UniversityClass> {

    @Mapping(source = "universityYear.id", target = "universityYearId")
    UniversityClassDTO toDto(UniversityClass universityClass);


    UniversityClassMentorshipDTO toMentorshipDto(UniversityClass universityClass);

    @Mapping(target = "mentorSubjects", ignore = true)
    @Mapping(target = "removeMentorSubjects", ignore = true)
    @Mapping(source = "universityYearId", target = "universityYear")
    UniversityClass toEntity(UniversityClassDTO universityClassDTO);

    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "id", ignore = true)
    UniversityClass toEntity(poli.meets.coreservice.service.dto.UniversityClassDTO universityClassDTO);

    default UniversityClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniversityClass universityClass = new UniversityClass();
        universityClass.setId(id);
        return universityClass;
    }
}
