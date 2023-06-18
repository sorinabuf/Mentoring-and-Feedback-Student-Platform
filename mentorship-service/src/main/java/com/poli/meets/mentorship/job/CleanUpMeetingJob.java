package com.poli.meets.mentorship.job;

import com.poli.meets.mentorship.domain.MeetingRequest;
import com.poli.meets.mentorship.repository.MeetingRequestRepository;
import com.poli.meets.mentorship.repository.MeetingSlotRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@AllArgsConstructor
public class CleanUpMeetingJob {
    private final MeetingRequestRepository meetingRequestRepository;

    private final MeetingSlotRepository meetingSlotRepository;

    @Transactional
    @Scheduled(fixedRate = 60000) // Run every minute
    public void executeJob() {
        deleteOldMeetings();
    }

    public void deleteOldMeetings() {
        meetingRequestRepository.deleteByMeetingSlot_DateBefore(Instant.now());
        meetingSlotRepository.deleteByDateBefore(Instant.now());
    }
}
