package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.client.MailClient;
import com.poli.meets.mentorship.domain.MeetingSlot;
import com.poli.meets.mentorship.domain.Mentor;
import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import com.poli.meets.mentorship.domain.enumeration.MeetingType;
import com.poli.meets.mentorship.repository.MeetingRequestRepository;
import com.poli.meets.mentorship.repository.MeetingSlotRepository;
import com.poli.meets.mentorship.repository.MentorRepository;
import com.poli.meets.mentorship.repository.StudentRepository;
import com.poli.meets.mentorship.service.dto.EmailDTO;
import com.poli.meets.mentorship.service.dto.MeetingDTO;
import com.poli.meets.mentorship.service.dto.MeetingRequestDTO;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.mapper.MeetingRequestMapper;
import com.poli.meets.mentorship.domain.MeetingRequest;

import com.poli.meets.mentorship.service.mapper.MeetingSlotMapper;
import com.poli.meets.mentorship.service.mapper.StudentMapper;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import com.poli.meets.mentorship.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link MeetingRequest}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class MeetingRequestService {

    private final MeetingRequestRepository meetingRequestRepository;

    private final MeetingSlotRepository meetingSlotRepository;

    private final MentorRepository mentorRepository;

    private final MeetingRequestMapper meetingRequestMapper;

    private final MeetingSlotMapper meetingSlotMapper;

    private final StudentMapper studentMapper;

    private final StudentRepository studentRepository;

    private final AuthClient authClient;

    private final MailClient mailClient;

    /**
     * Save a meetingRequest.
     *
     * @param meetingRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public MeetingRequestDTO save(MeetingRequestDTO meetingRequestDTO) {
        log.debug("Request to save MeetingRequest : {}", meetingRequestDTO);
        MeetingRequest meetingRequest = meetingRequestMapper.toEntity(meetingRequestDTO);
        meetingRequest = meetingRequestRepository.save(meetingRequest);
        return meetingRequestMapper.toDto(meetingRequest);
    }

    /**
     * Get all the meetingRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MeetingRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeetingRequests");
        return meetingRequestRepository.findAll(pageable)
                .map(meetingRequestMapper::toDto);
    }


    /**
     * Get one meetingRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingRequestDTO> findOne(Long id) {
        log.debug("Request to get MeetingRequest : {}", id);
        return meetingRequestRepository.findById(id)
                .map(meetingRequestMapper::toDto);
    }

    /**
     * Delete the meetingRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingRequest : {}", id);
        meetingRequestRepository.deleteById(id);
    }

    public List<MeetingDTO> findAllUpcomingMeetings(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor mentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                        .findAny()
                        .orElseThrow(BadRequestException::new);

        List<MeetingDTO> meetingRequestsMentors = getMentorMeetings(student);

        List<MeetingDTO> meetingRequestsMentees =
                meetingRequestRepository.findByStatusAndMeetingSlot_DateAfterAndMeetingSlot_Mentor_Id
                                (MeetingRequestStatus.APPROVED, Instant.now(), mentor.getId()).stream()
                        .map(meetingRequestMapper::toMeetingDto)
                        .collect(Collectors.toList());

        for (MeetingDTO meetingDTO : meetingRequestsMentees) {
            meetingDTO.setType(MeetingType.MENTEE);
            Mentor meetingMentor =
                    mentorRepository.findById(meetingDTO.getMeetingSlot().getMentorId())
                            .stream().findAny()
                            .orElseThrow(BadRequestException::new);
            meetingDTO.setMentor(studentMapper.toDto(meetingMentor.getStudent()));
        }

        List<MeetingDTO> allUpcomingMeetings = new ArrayList<>();
        allUpcomingMeetings.addAll(meetingRequestsMentors);
        allUpcomingMeetings.addAll(meetingRequestsMentees);

        allUpcomingMeetings.sort(Comparator.comparing(a -> a.getMeetingSlot().getDate()));

        return allUpcomingMeetings;
    }

    public List<MeetingDTO> findStudentUpcomingMeetings(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        getMentorMeetings(student);
        List<MeetingDTO> meetingRequestsMentors = getMentorMeetings(student);
        meetingRequestsMentors.sort(Comparator.comparing(a -> a.getMeetingSlot().getDate()));

        return meetingRequestsMentors;
    }

    private List<MeetingDTO> getMentorMeetings(Student student) {
        List<MeetingDTO> meetingRequestsMentors =
                meetingRequestRepository.findByStudentIdAndStatusAndMeetingSlot_DateAfter
                                (student.getId(), MeetingRequestStatus.APPROVED, Instant.now()).stream()
                        .map(meetingRequestMapper::toMeetingDto)
                        .collect(Collectors.toList());

        for (MeetingDTO meetingDTO : meetingRequestsMentors) {
            meetingDTO.setType(MeetingType.MENTOR);
            Mentor meetingMentor =
                    mentorRepository.findById(meetingDTO.getMeetingSlot().getMentorId())
                            .stream().findAny()
                            .orElseThrow(BadRequestException::new);
            meetingDTO.setMentor(studentMapper.toDto(meetingMentor.getStudent()));
        }

        return meetingRequestsMentors;
    }

    public void deleteUpcomingMeeting(MeetingDTO meetingDTO) {
        meetingRequestRepository.deleteById(meetingDTO.getId());

        MeetingSlot meetingSlot =
                meetingSlotMapper.toEntity(meetingDTO.getMeetingSlot());
        meetingSlot.setStatus("open");
        meetingSlotRepository.save(meetingSlot);

        EmailDTO emailDTO = new EmailDTO();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        Instant date = meetingDTO.getMeetingSlot().getDate();
        String url = "http://localhost:4200";

        emailDTO.setTo(meetingDTO.getStudent().getPersonalEmail());
        emailDTO.setSubject("[PoliMeets] Upcoming meeting status update");
        emailDTO.setBody("Hello " + meetingDTO.getStudent().getFirstName() +
                ",<br><br>" + "The meeting from <b>" + formatter.format(date) +
                "</b> with <b>" + meetingDTO.getMentor().getFirstName() + ' ' +
                meetingDTO.getMentor().getLastName() +
                "</b> has been canceled by one of the participants." +
                "<br><br>You can plan other meetings by visiting us at " + url);

        mailClient.sendEmail(emailDTO);

        emailDTO.setTo(meetingDTO.getMentor().getPersonalEmail());
        emailDTO.setBody("Hello " + meetingDTO.getMentor().getFirstName() +
                ",<br><br>" + "The meeting from <b>" + formatter.format(date) +
                "</b> with <b>" + meetingDTO.getStudent().getFirstName() + ' ' +
                meetingDTO.getStudent().getLastName() +
                "</b> has been canceled by one of the participants." +
                "<br><br>You can plan other meetings by visiting us at " + url);

        mailClient.sendEmail(emailDTO);
    }

    private static final String DATE_PATTERN_FORMAT = "dd.MM.yyyy HH:mm";
}
