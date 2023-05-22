package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.MeetingRequest;
import com.poli.meets.mentorship.service.dto.MeetingRequestDTO;
import com.poli.meets.mentorship.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeetingRequest} and its DTO {@link MeetingRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {MeetingSlotMapper.class, StudentMapper.class})
public interface MeetingRequestMapper extends EntityMapper<MeetingRequestDTO, MeetingRequest> {

    @Mapping(source = "meetingSlot.id", target = "meetingSlotId")
    @Mapping(source = "student.id", target = "studentId")
    MeetingRequestDTO toDto(MeetingRequest meetingRequest);

    @Mapping(source = "meetingSlotId", target = "meetingSlot")
    @Mapping(source = "studentId", target = "student")
    MeetingRequest toEntity(MeetingRequestDTO meetingRequestDTO);

    default MeetingRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setId(id);
        return meetingRequest;
    }
}
