package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.client.MailClient;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import com.poli.meets.mentorship.domain.enumeration.MeetingSlotStatus;
import com.poli.meets.mentorship.domain.enumeration.MeetingType;
import com.poli.meets.mentorship.repository.*;
import com.poli.meets.mentorship.service.dto.EmailDTO;
import com.poli.meets.mentorship.service.dto.MeetingDTO;
import com.poli.meets.mentorship.service.dto.MeetingRequestDTO;
import com.poli.meets.mentorship.service.mapper.MeetingRequestMapper;

import com.poli.meets.mentorship.service.mapper.MeetingSlotMapper;
import com.poli.meets.mentorship.service.mapper.StudentMapper;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import com.poli.meets.mentorship.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
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

    private final MentorSubjectRepository mentorSubjectRepository;

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

    public List<MeetingDTO> findAllMeetings(String token,
                                            MeetingRequestStatus meetingRequestStatus) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor mentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                        .findAny()
                        .orElseThrow(BadRequestException::new);

        List<MeetingDTO> meetingRequestsMentors = getMentorMeetings(student,
                meetingRequestStatus);

        List<MeetingDTO> meetingRequestsMentees =
                meetingRequestRepository.findByStatusAndMeetingSlot_DateAfterAndMeetingSlot_Mentor_Id
                                (meetingRequestStatus, Instant.now(), mentor.getId()).stream()
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

        List<MeetingDTO> allMeetings = new ArrayList<>();
        allMeetings.addAll(meetingRequestsMentors);
        allMeetings.addAll(meetingRequestsMentees);

        allMeetings.sort(Comparator.comparing(a -> a.getMeetingSlot().getDate()));

        return allMeetings;
    }

    public List<MeetingDTO> findStudentMeetings(String token,
                                                MeetingRequestStatus meetingRequestStatus) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        List<MeetingDTO> meetingRequestsMentors = getMentorMeetings(student,
                meetingRequestStatus);
        meetingRequestsMentors.sort(Comparator.comparing(a -> a.getMeetingSlot().getDate()));

        return meetingRequestsMentors;
    }

    private List<MeetingDTO> getMentorMeetings(Student student,
                                               MeetingRequestStatus meetingRequestStatus) {
        List<MeetingDTO> meetingRequestsMentors =
                meetingRequestRepository.findByStudentIdAndStatusAndMeetingSlot_DateAfter
                                (student.getId(), meetingRequestStatus, Instant.now()).stream()
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

    public void deleteMeeting(MeetingDTO meetingDTO, MeetingRequestStatus meetingRequestStatus) {
        meetingRequestRepository.deleteById(meetingDTO.getId());

        MeetingSlot meetingSlot =
                meetingSlotMapper.toEntity(meetingDTO.getMeetingSlot());
        meetingSlot.setStatus(MeetingSlotStatus.OPEN);
        meetingSlotRepository.save(meetingSlot);

        if (meetingRequestStatus == MeetingRequestStatus.APPROVED) {
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
        } else if (meetingRequestStatus == MeetingRequestStatus.PENDING) {
            EmailDTO emailDTO = new EmailDTO();
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT)
                            .withZone(ZoneId.systemDefault());
            Instant date = meetingSlot.getDate();
            String url = "http://localhost:4200";

            emailDTO.setTo(meetingDTO.getStudent().getPersonalEmail());
            emailDTO.setSubject("[PoliMeets] Pending meeting status update");
            emailDTO.setBody("Hello " + meetingDTO.getStudent().getFirstName() +
                    ",<br><br>" + "The meeting requested for <b>"
                    + formatter.format(date) + "</b> with <b>" +
                    meetingDTO.getMentor().getFirstName() + ' '
                    + meetingDTO.getMentor().getLastName() +
                    "</b> has been declined by one of the participants" +
                    ".<br><br>You can plan other " +
                    "meetings by visiting us at " + url);

            mailClient.sendEmail(emailDTO);
        }
    }

    public MeetingRequestDTO saveStudentRequest(String token,
                                                MeetingRequestDTO meetingRequestDTO) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        MeetingRequest meetingRequest = meetingRequestMapper.toEntity(meetingRequestDTO);
        meetingRequest.setStudent(student);

        MeetingSlot meetingSlot =
                meetingSlotRepository.findById(meetingRequestDTO.getMeetingSlotId())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        MentorSubject mentorSubject =
                mentorSubjectRepository.findByMentorIdAndUniversityClass_Id(
                        meetingSlot.getMentor().getId(),
                        meetingRequestDTO.getMentorSubjectId())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        meetingRequest.setMentorSubject(mentorSubject);
        meetingRequest = meetingRequestRepository.save(meetingRequest);

        meetingSlot.setStatus(MeetingSlotStatus.CLOSED);
        meetingSlotRepository.save(meetingSlot);

        EmailDTO emailDTO = new EmailDTO();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT)
                        .withZone(ZoneId.systemDefault());
        Instant date = meetingSlot.getDate();
        String url = "http://localhost:4200";

        emailDTO.setTo(student.getPersonalEmail());
        emailDTO.setSubject("[PoliMeets] Pending meeting request");
        emailDTO.setBody("Hello " + student.getFirstName() +
                ",<br><br>" + "You issued a meeting request for <b>"
                + formatter.format(date) + "</b> with <b>" +
                meetingSlot.getMentor().getStudent().getFirstName() + ' ' +
                meetingSlot.getMentor().getStudent().getLastName() + "</b>. " +
                "You will receive a status update email once your request is " +
                "confirmed or rejected. <br><br>You can plan other meetings " +
                "by visiting us at " + url);

        mailClient.sendEmail(emailDTO);

        emailDTO.setTo(meetingSlot.getMentor().getStudent().getPersonalEmail());
        emailDTO.setBody("Hello " + meetingSlot.getMentor().getStudent().getFirstName() +
                ",<br><br>" + "Your meeting slot from <b>"
                + formatter.format(date) + "</b> has been requested by <b> " +
                student.getFirstName() + ' ' + student.getLastName() + "</b>." +
                "<br><br>You can review your pending meetings by logging into" +
                " your personal PoliMeets account at " + url);

        mailClient.sendEmail(emailDTO);

        return meetingRequestMapper.toDto(meetingRequest);
    }

    public void approveMeeting(MeetingDTO meetingDTO) {
        Optional<MeetingRequest> meetingRequestOptional =
                meetingRequestRepository.findById(meetingDTO.getId());

        if (meetingRequestOptional.isPresent()) {
            MeetingRequest meetingRequest = meetingRequestOptional.get();
            meetingRequest.setStatus(MeetingRequestStatus.APPROVED);
            meetingRequest = meetingRequestRepository.save(meetingRequest);

            Student student = meetingRequest.getStudent();
            Student mentor =
                    meetingRequest.getMeetingSlot().getMentor().getStudent();
            MeetingSlot meetingSlot = meetingRequest.getMeetingSlot();

            EmailDTO emailDTO = new EmailDTO();
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT)
                            .withZone(ZoneId.systemDefault());
            Instant date = meetingSlot.getDate();
            String url = "http://localhost:4200";

            emailDTO.setTo(student.getPersonalEmail());
            emailDTO.setSubject("[PoliMeets] Pending meeting status update");
            emailDTO.setBody("Hello " + student.getFirstName() +
                    ",<br><br>" + "The meeting requested for <b>"
                    + formatter.format(date) + "</b> with <b>" +
                    mentor.getFirstName() + ' ' + mentor.getLastName() +
                    "</b> has been approved.<br><br>You can plan other " +
                    "meetings by visiting us at " + url);

            mailClient.sendEmail(emailDTO);

            emailDTO.setTo(mentor.getPersonalEmail());
            emailDTO.setBody("Hello " + mentor.getFirstName() +
                    ",<br><br>" + "Your meeting slot from <b>"
                    + formatter.format(date) + "</b> has been booked by <b> " +
                    student.getFirstName() + ' ' + student.getLastName() + "</b>." +
                    "<br><br>You can review your upcoming meetings by logging" +
                    " into your personal PoliMeets account at " + url);

            mailClient.sendEmail(emailDTO);
        }
    }

    private static final String DATE_PATTERN_FORMAT = "dd.MM.yyyy HH:mm";
}
