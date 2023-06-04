package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.MeetingRequest;

import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MeetingRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, Long>, JpaSpecificationExecutor<MeetingRequest> {
    List<MeetingRequest> findByStudentIdAndStatusAndMeetingSlot_DateAfter(
            Long studentId, MeetingRequestStatus status, Instant date);

    List<MeetingRequest> findByStatusAndMeetingSlot_DateAfterAndMeetingSlot_Mentor_Id(
            MeetingRequestStatus status, Instant date, Long mentorId);

    List<MeetingRequest> findByMentorSubject_Id(Long mentorSubjectId);

    List<MeetingRequest> findByMeetingSlot_Mentor_Id(Long mentorId);

    void deleteByMentorSubjectId(Long mentorSubjectId);

    void deleteByMeetingSlot_Mentor_Id(Long mentorId);
}
