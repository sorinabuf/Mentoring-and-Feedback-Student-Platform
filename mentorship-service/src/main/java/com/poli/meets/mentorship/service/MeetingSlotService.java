package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.domain.Mentor;
import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.domain.enumeration.MeetingSlotStatus;
import com.poli.meets.mentorship.repository.MeetingSlotRepository;
import com.poli.meets.mentorship.repository.MentorRepository;
import com.poli.meets.mentorship.repository.StudentRepository;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.dto.MeetingSlotPostDTO;
import com.poli.meets.mentorship.service.mapper.MeetingSlotMapper;
import com.poli.meets.mentorship.domain.MeetingSlot;

import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import com.poli.meets.mentorship.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link MeetingSlot}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class MeetingSlotService {

    private final MeetingSlotRepository meetingSlotRepository;

    private final MentorRepository mentorRepository;

    private final StudentRepository studentRepository;

    private final MeetingSlotMapper meetingSlotMapper;

    private final AuthClient authClient;

    /**
     * Save a meetingSlot.
     *
     * @param meetingSlotDTO the entity to save.
     * @return the persisted entity.
     */
    public MeetingSlotDTO save(MeetingSlotDTO meetingSlotDTO) {
        log.debug("Request to save MeetingSlot : {}", meetingSlotDTO);
        MeetingSlot meetingSlot = meetingSlotMapper.toEntity(meetingSlotDTO);
        meetingSlot = meetingSlotRepository.save(meetingSlot);
        return meetingSlotMapper.toDto(meetingSlot);
    }

    /**
     * Get all the meetingSlots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MeetingSlotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeetingSlots");
        return meetingSlotRepository.findAll(pageable)
            .map(meetingSlotMapper::toDto);
    }


    /**
     * Get one meetingSlot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingSlotDTO> findOne(Long id) {
        log.debug("Request to get MeetingSlot : {}", id);
        return meetingSlotRepository.findById(id)
            .map(meetingSlotMapper::toDto);
    }

    /**
     * Delete the meetingSlot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingSlot : {}", id);
        meetingSlotRepository.deleteById(id);
    }

    public List<MeetingSlotDTO> findFreeSlots(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor mentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                        .findAny()
                        .orElseThrow(BadRequestException::new);

        List<MeetingSlot> meetingSlots =
                meetingSlotRepository.findByMentorIdAndStatusAndDateAfter(
                mentor.getId(), MeetingSlotStatus.OPEN, Instant.now());

        meetingSlots.sort(Comparator.comparing(MeetingSlot::getDate));

        return meetingSlots.stream().map(meetingSlotMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MeetingSlotDTO> findFreeSlots(Long mentorId) {
        List<MeetingSlot> meetingSlots =
                meetingSlotRepository.findByMentorIdAndStatusAndDateAfter(
                        mentorId, MeetingSlotStatus.OPEN, Instant.now());

        meetingSlots.sort(Comparator.comparing(MeetingSlot::getDate));

        return meetingSlots.stream().map(meetingSlotMapper::toDto)
                .collect(Collectors.toList());
    }

    public MeetingSlotDTO save(String token,
                               MeetingSlotPostDTO meetingSlotDTO) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor mentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                        .findAny()
                        .orElseThrow(BadRequestException::new);

        MeetingSlot meetingSlot = new MeetingSlot();
        meetingSlot.setDate(meetingSlotDTO.getDate());
        meetingSlot.setStatus(MeetingSlotStatus.OPEN);
        meetingSlot.setMentor(mentor);

        meetingSlot = meetingSlotRepository.save(meetingSlot);
        return meetingSlotMapper.toDto(meetingSlot);
    }
}
