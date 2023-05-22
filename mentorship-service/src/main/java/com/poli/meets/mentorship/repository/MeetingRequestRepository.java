package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.MeetingRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the MeetingRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, Long>, JpaSpecificationExecutor<MeetingRequest> {
}
