package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.domain.MeetingSlot;
import com.poli.meets.mentorship.domain.Mentor;
import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import com.poli.meets.mentorship.domain.enumeration.MeetingType;
import com.poli.meets.mentorship.repository.MeetingRequestRepository;
import com.poli.meets.mentorship.repository.MeetingSlotRepository;
import com.poli.meets.mentorship.repository.MentorRepository;
import com.poli.meets.mentorship.repository.StudentRepository;
import com.poli.meets.mentorship.service.dto.MeetingDTO;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.dto.MentorSlotDTO;
import com.poli.meets.mentorship.service.dto.StudentDTO;
import com.poli.meets.mentorship.service.mapper.MeetingRequestMapper;
import com.poli.meets.mentorship.service.mapper.MeetingSlotMapper;
import com.poli.meets.mentorship.service.mapper.StudentMapper;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import com.poli.meets.mentorship.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class MentorSlotService {
    private final MeetingRequestRepository meetingRequestRepository;

    private final MeetingSlotRepository meetingSlotRepository;

    private final MentorRepository mentorRepository;

    private final MeetingRequestMapper meetingRequestMapper;

    private final MeetingSlotMapper meetingSlotMapper;

    private final StudentMapper studentMapper;

    private final StudentRepository studentRepository;

    private final AuthClient authClient;

    public List<MentorSlotDTO> findAllMentorSlots(String token) {
        List<MentorSlotDTO> mentorSlotDTOS = new ArrayList<>();

        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        Mentor mentor =
                mentorRepository.findByStudentId(student.getId()).stream()
                        .findAny()
                        .orElseThrow(BadRequestException::new);

        List<MeetingDTO> meetingRequestsMentors =
                meetingRequestRepository.findByStudentIdAndStatusAndMeetingSlot_DateAfter
                                (student.getId(), MeetingRequestStatus.APPROVED, Instant.now()).stream()
                        .map(meetingRequestMapper::toMeetingDto)
                        .collect(Collectors.toList());

        for (MeetingDTO meetingDTO : meetingRequestsMentors) {
            MentorSlotDTO mentorSlotDTO= new MentorSlotDTO();
            mentorSlotDTO.setType(MeetingType.MENTOR);
            mentorSlotDTO.setDate(meetingDTO.getMeetingSlot().getDate());
            Mentor meetingMentor =
                    mentorRepository.findById(meetingDTO.getMeetingSlot().getMentorId())
                            .stream().findAny()
                            .orElseThrow(BadRequestException::new);
            mentorSlotDTO.setName(meetingMentor.getStudent().getFirstName() + ' '
                    + meetingMentor.getStudent().getLastName());
            mentorSlotDTO.setEmail(meetingMentor.getStudent().getPersonalEmail());
            mentorSlotDTO.setImage(studentMapper.toDto(meetingMentor.getStudent())
                    .getImage());
            mentorSlotDTOS.add(mentorSlotDTO);
        }

        List<MeetingDTO> meetingRequestsMentees =
                meetingRequestRepository.findByStatusAndMeetingSlot_DateAfterAndMeetingSlot_Mentor_Id
                                (MeetingRequestStatus.APPROVED, Instant.now(), mentor.getId()).stream()
                        .map(meetingRequestMapper::toMeetingDto)
                        .collect(Collectors.toList());

        for (MeetingDTO meetingDTO : meetingRequestsMentees) {
            MentorSlotDTO mentorSlotDTO= new MentorSlotDTO();
            mentorSlotDTO.setType(MeetingType.MENTEE);
            mentorSlotDTO.setDate(meetingDTO.getMeetingSlot().getDate());
            mentorSlotDTO.setName(meetingDTO.getStudent().getFirstName() + ' '
                    + meetingDTO.getStudent().getLastName());
            mentorSlotDTO.setEmail(meetingDTO.getStudent().getPersonalEmail());
            mentorSlotDTO.setImage(meetingDTO.getStudent().getImage());
            mentorSlotDTOS.add(mentorSlotDTO);
        }

        List<MeetingSlotDTO> meetingSlots =
                meetingSlotRepository.findByMentorIdAndStatusAndDateAfter(
                        mentor.getId(), "open", Instant.now()).stream()
                        .map(meetingSlotMapper::toDto)
                        .collect(Collectors.toList());


        for (MeetingSlotDTO meetingSlotDTO : meetingSlots) {
            MentorSlotDTO mentorSlotDTO= new MentorSlotDTO();
            mentorSlotDTO.setDate(meetingSlotDTO.getDate());
            mentorSlotDTO.setName("Unknown");
            mentorSlotDTO.setEmail("unknown");
            mentorSlotDTOS.add(mentorSlotDTO);
        }

        mentorSlotDTOS.sort(Comparator.comparing(MentorSlotDTO::getDate));

        return mentorSlotDTOS;
    }
}
