package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.MeetingSlot;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MeetingSlot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingSlotRepository extends JpaRepository<MeetingSlot, Long>, JpaSpecificationExecutor<MeetingSlot> {
    List<MeetingSlot> findByMentorIdAndStatusAndDateAfter(Long mentorId,
                                                           String status,
                                                           Instant date);
}
