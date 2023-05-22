package com.poli.meets.mentorship.service.mapper;


import com.poli.meets.mentorship.domain.MeetingSlot;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeetingSlot} and its DTO {@link MeetingSlotDTO}.
 */
@Mapper(componentModel = "spring", uses = {MentorMapper.class})
public interface MeetingSlotMapper extends EntityMapper<MeetingSlotDTO, MeetingSlot> {

    @Mapping(source = "mentor.id", target = "mentorId")
    MeetingSlotDTO toDto(MeetingSlot meetingSlot);

    @Mapping(target = "meetingRequests", ignore = true)
    @Mapping(target = "removeMeetingRequests", ignore = true)
    @Mapping(source = "mentorId", target = "mentor")
    MeetingSlot toEntity(MeetingSlotDTO meetingSlotDTO);

    default MeetingSlot fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeetingSlot meetingSlot = new MeetingSlot();
        meetingSlot.setId(id);
        return meetingSlot;
    }
}
