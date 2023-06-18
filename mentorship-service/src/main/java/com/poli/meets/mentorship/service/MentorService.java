package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.client.MailClient;
import com.poli.meets.mentorship.domain.*;
import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import com.poli.meets.mentorship.domain.enumeration.MeetingSlotStatus;
import com.poli.meets.mentorship.domain.enumeration.Year;
import com.poli.meets.mentorship.repository.*;
import com.poli.meets.mentorship.service.dto.*;
import com.poli.meets.mentorship.service.mapper.*;

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
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link Mentor}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class MentorService {

    private final MentorRepository mentorRepository;

    private final MeetingRequestRepository meetingRequestRepository;

    private final MeetingSlotRepository meetingSlotRepository;

    private final MentorMapper mentorMapper;

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final UniversityClassRepository universityClassRepository;

    private final UniversityClassMapper universityClassMapper;

    private final MentorSkillRepository mentorSkillRepository;

    private final MentorSubjectRepository mentorSubjectRepository;

    private final AuthClient authClient;

    private final MailClient mailClient;

    /**
     * Save a mentor.
     *
     * @param mentorDTO the entity to save.
     * @return the persisted entity.
     */
    public MentorDTO save(MentorDTO mentorDTO) {
        log.debug("Request to save Mentor : {}", mentorDTO);
        Mentor mentor = mentorMapper.toEntity(mentorDTO);
        mentor = mentorRepository.save(mentor);
        return mentorMapper.toDto(mentor);
    }

    /**
     * Get all the mentors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MentorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mentors");
        return mentorRepository.findAll(pageable)
            .map(mentorMapper::toDto);
    }


    /**
     * Get one mentor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MentorDTO> findOne(Long id) {
        log.debug("Request to get Mentor : {}", id);
        return mentorRepository.findById(id)
            .map(mentorMapper::toDto);
    }

    public void delete(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor mentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                        .findAny()
                        .orElseThrow(BadRequestException::new);

        Long id = mentor.getId();

        mentorSkillRepository.deleteByMentorId(id);

        List<MeetingRequest> meetingRequests =
                meetingRequestRepository.findByMeetingSlot_Mentor_Id(id);

        for (MeetingRequest meetingRequest : meetingRequests) {
            if (meetingRequest.getStatus() == MeetingRequestStatus.APPROVED
                && meetingRequest.getMeetingSlot().getDate().compareTo(Instant.now()) > 0) {
                EmailDTO emailDTO = new EmailDTO();
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT)
                                .withZone(ZoneId.systemDefault());
                Instant date = meetingRequest.getMeetingSlot().getDate();
                String url = "http://localhost:4200";

                emailDTO.setTo(meetingRequest.getStudent().getPersonalEmail());
                emailDTO.setSubject("[PoliMeets] Upcoming meeting status update");
                emailDTO.setBody("Hello " + meetingRequest.getStudent().getFirstName() +
                        ",<br><br>" + "The meeting from <b>" + formatter.format(date) +
                        "</b> with <b>" + meetingRequest.getMeetingSlot().getMentor().getStudent().getFirstName() + ' ' +
                        meetingRequest.getMeetingSlot().getMentor().getStudent().getLastName() +
                        "</b> has been canceled by one of the participants." +
                        "<br><br>You can plan other meetings by visiting us at " + url);

                mailClient.sendEmail(emailDTO);

            } else if (meetingRequest.getStatus() == MeetingRequestStatus.PENDING
                    && meetingRequest.getMeetingSlot().getDate().compareTo(Instant.now()) > 0) {
                EmailDTO emailDTO = new EmailDTO();
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT)
                                .withZone(ZoneId.systemDefault());
                Instant date = meetingRequest.getMeetingSlot().getDate();
                String url = "http://localhost:4200";

                emailDTO.setTo(meetingRequest.getStudent().getPersonalEmail());
                emailDTO.setSubject("[PoliMeets] Pending meeting status " +
                        "update");
                emailDTO.setBody("Hello " + meetingRequest.getStudent().getFirstName() +
                        ",<br><br>" + "The pending request from <b>" + formatter.format(date) +
                        "</b> with <b>" + meetingRequest.getMeetingSlot().getMentor().getStudent().getFirstName() + ' ' +
                        meetingRequest.getMeetingSlot().getMentor().getStudent().getLastName() +
                        "</b> has been rejected. <br><br>You can plan other " +
                        "meetings by visiting us at " + url);

                mailClient.sendEmail(emailDTO);
            }
        }

        meetingRequestRepository.deleteByMeetingSlot_Mentor_Id(id);
        meetingSlotRepository.deleteByMentorId(id);
        mentorSubjectRepository.deleteByMentorId(id);
        mentorRepository.deleteById(id);
    }

    public MentorInfoDTO findCurrentMentor(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor mentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                .findAny()
                .orElseThrow(BadRequestException::new);

        MentorInfoDTO mentorInfoDTO = new MentorInfoDTO();
        mentorInfoDTO.setId(mentor.getId());
        mentorInfoDTO.setDescription(mentor.getDescription());
        mentorInfoDTO.setSkills(skillRepository.findMentorSkills(mentor.getId())
                .stream().map(skillMapper::toDto).collect(Collectors.toList()));
        mentorInfoDTO.setSubjects(universityClassRepository.findMentorSubjects(mentor.getId())
                .stream().map(universityClassMapper::toDto).collect(Collectors.toList()));

        return mentorInfoDTO;
    }

    public List<MentorInfoDTO> findStudentMentors(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        List<Mentor> mentors =
                mentorRepository.findByStudent_UniversityYear_Faculty_Id(
                        student.getUniversityYear().getFaculty().getId())
                        .stream().filter(mentor -> !Objects.equals(mentor.getStudent().getId(),
                                student.getId()))
                        .collect(Collectors.toList());

        List<MentorInfoDTO> mentorInfoDTOS = new ArrayList<>();

        for(Mentor mentor : mentors) {
            MentorInfoDTO mentorInfoDTO = new MentorInfoDTO();
            mentorInfoDTO.setId(mentor.getId());
            mentorInfoDTO.setDescription(mentor.getDescription());
            mentorInfoDTO.setSkills(skillRepository.findMentorSkills(mentor.getId())
                    .stream().map(skillMapper::toDto).collect(Collectors.toList()));
            mentorInfoDTO.setSubjects(universityClassRepository.findMentorSubjects(mentor.getId())
                    .stream().map(universityClassMapper::toDto).collect(Collectors.toList()));
            mentorInfoDTO.setStudent(studentMapper.toDto(mentor.getStudent()));

            mentorInfoDTOS.add(mentorInfoDTO);
        }

        return mentorInfoDTOS;
    }

    public MentorDTO save(String token, MentorInfoDTO mentorDTO) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        List<Mentor> existentMentor =
                mentorRepository.findByStudentId(student.getId());

        if (existentMentor.size() > 0) {
            throw new BadRequestException();
        }

        Mentor mentor = new Mentor();
        mentor.setDescription(mentorDTO.getDescription());
        mentor.setStudent(student);

        mentor = mentorRepository.save(mentor);

        return setMentorInfo(mentorDTO, mentor);
    }

    public MentorDTO update(String token, MentorInfoDTO mentorDTO) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor currentMentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                        .findAny()
                        .orElseThrow(BadRequestException::new);

        if (!Objects.equals(currentMentor.getId(), mentorDTO.getId())) {
            throw new BadRequestException();
        }

        Mentor mentor = new Mentor();
        mentor.setId(mentorDTO.getId());
        mentor.setDescription(mentorDTO.getDescription());
        mentor.setStudent(student);

        mentor = mentorRepository.save(mentor);

        mentorSkillRepository.deleteByMentorId(mentor.getId());

        for (SkillDTO skillDTO : mentorDTO.getSkills()) {
            MentorSkill mentorSkill = new MentorSkill();
            mentorSkill.setMentor(mentor);
            mentorSkill.setSkill(skillMapper.toEntity(skillDTO));
            mentorSkillRepository.save(mentorSkill);
        }

        List<MentorSubject> currentMentorSubjects =
                mentorSubjectRepository.findByMentorId(mentor.getId());

        List<Long> newMentorSubjectsIds =
                mentorDTO.getSubjects().stream().map(universityClassMapper::toEntity)
                        .map(UniversityClass::getId)
                        .collect(Collectors.toList());

        for (MentorSubject mentorSubject : currentMentorSubjects) {
            if (!newMentorSubjectsIds.contains(mentorSubject.getUniversityClass().getId())) {
                List<MeetingRequest> meetingRequests =
                        meetingRequestRepository.findByMentorSubject_Id(mentorSubject.getId());

                for (MeetingRequest meetingRequest : meetingRequests) {
                    MeetingSlot meetingSlot =
                            meetingSlotRepository.findById(meetingRequest.getMeetingSlot().getId())
                                    .stream().findAny()
                                    .orElseThrow(ForbiddenException::new);
                    meetingSlot.setStatus(MeetingSlotStatus.OPEN);
                    meetingSlotRepository.save(meetingSlot);

                    if (meetingRequest.getMeetingSlot().getDate().compareTo(Instant.now()) > 0) {
                        EmailDTO emailDTO = new EmailDTO();
                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT)
                                        .withZone(ZoneId.systemDefault());
                        Instant date = meetingSlot.getDate();
                        String url = "http://localhost:4200";

                        if (meetingRequest.getStatus() == MeetingRequestStatus.APPROVED) {
                            emailDTO.setTo(meetingRequest.getStudent().getPersonalEmail());
                            emailDTO.setSubject("[PoliMeets] Upcoming meeting status update");
                            emailDTO.setBody("Hello " + meetingRequest.getStudent().getFirstName() +
                                    ",<br><br>" + "The meeting from <b>" + formatter.format(date) +
                                    "</b> with <b>" + meetingSlot.getMentor().getStudent().getFirstName() + ' ' +
                                    meetingSlot.getMentor().getStudent().getLastName() +
                                    "</b> has been canceled by one of the participants." +
                                    "<br><br>You can plan other meetings by visiting us at " + url);

                            mailClient.sendEmail(emailDTO);

                            emailDTO.setTo(meetingSlot.getMentor().getStudent().getPersonalEmail());
                            emailDTO.setBody("Hello " + meetingSlot.getMentor().getStudent().getFirstName() +
                                    ",<br><br>" + "The meeting from <b>" + formatter.format(date) +
                                    "</b> with <b>" + meetingRequest.getStudent().getFirstName() + ' ' +
                                    meetingRequest.getStudent().getLastName() +
                                    "</b> has been canceled by one of the participants." +
                                    "<br><br>You can plan other meetings by visiting us at " + url);

                            mailClient.sendEmail(emailDTO);
                        } else if (meetingRequest.getStatus() == MeetingRequestStatus.PENDING) {
                            emailDTO.setTo(meetingRequest.getStudent().getPersonalEmail());
                            emailDTO.setSubject("[PoliMeets] Pending meeting " +
                                    "status update");
                            emailDTO.setBody("Hello " + meetingRequest.getStudent().getFirstName() +
                                    ",<br><br>" + "The pending request from " +
                                    "<b>" + formatter.format(date) +
                                    "</b> with <b>" + meetingSlot.getMentor().getStudent().getFirstName() + ' ' +
                                    meetingSlot.getMentor().getStudent().getLastName() +
                                    "</b> has been rejected." +
                                    "<br><br>You can plan other meetings by visiting us at " + url);

                            mailClient.sendEmail(emailDTO);
                        }
                    }
                }

                meetingRequestRepository.deleteByMentorSubjectId(mentorSubject.getId());

                mentorSubjectRepository.deleteByMentorIdAndUniversityClass_Id(
                        mentor.getId(), mentorSubject.getUniversityClass().getId());
            }
        }

        List<Long> currentMentorSubjectsIds =
                currentMentorSubjects.stream().map(
                        subject -> subject.getUniversityClass().getId())
                        .collect(Collectors.toList());

        for (UniversityClassDTO universityClassDTO : mentorDTO.getSubjects()) {
            if (!currentMentorSubjectsIds.contains(universityClassDTO.getId())) {
                MentorSubject mentorSubject = new MentorSubject();
                mentorSubject.setMentor(mentor);
                mentorSubject.setUniversityClass(universityClassMapper.toEntity(universityClassDTO));
                mentorSubjectRepository.save(mentorSubject);
            }
        }

        return mentorMapper.toDto(mentor);
    }

    private MentorDTO setMentorInfo(MentorInfoDTO mentorDTO, Mentor mentor) {
        for (SkillDTO skillDTO : mentorDTO.getSkills()) {
            MentorSkill mentorSkill = new MentorSkill();
            mentorSkill.setMentor(mentor);
            mentorSkill.setSkill(skillMapper.toEntity(skillDTO));
            mentorSkillRepository.save(mentorSkill);
        }

        for (UniversityClassDTO universityClassDTO : mentorDTO.getSubjects()) {
            MentorSubject mentorSubject = new MentorSubject();
            mentorSubject.setMentor(mentor);
            mentorSubject.setUniversityClass(universityClassMapper.toEntity(universityClassDTO));
            mentorSubjectRepository.save(mentorSubject);
        }

        return mentorMapper.toDto(mentor);
    }

    private static final String DATE_PATTERN_FORMAT = "dd.MM.yyyy HH:mm";
}
