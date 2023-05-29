package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.domain.*;
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

import java.util.Optional;
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

    private final MentorMapper mentorMapper;

    private final SkillMapper skillMapper;

    private final UniversityClassMapper universityClassMapper;

    private final SkillRepository skillRepository;

    private final StudentRepository studentRepository;

    private final UniversityClassRepository universityClassRepository;

    private final MentorSkillRepository mentorSkillRepository;

    private final MentorSubjectRepository mentorSubjectRepository;

    private final AuthClient authClient;

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

    /**
     * Delete the mentor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mentor : {}", id);

        mentorSkillRepository.deleteByMentorId(id);
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

    public MentorDTO save(String token, MentorInfoDTO mentorDTO) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

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

        Mentor mentor = new Mentor();
        mentor.setId(mentorDTO.getId());
        mentor.setDescription(mentorDTO.getDescription());
        mentor.setStudent(student);

        mentor = mentorRepository.save(mentor);

        mentorSkillRepository.deleteByMentorId(mentor.getId());
        mentorSubjectRepository.deleteByMentorId(mentor.getId());

        return setMentorInfo(mentorDTO, mentor);
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
}
