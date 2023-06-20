package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.domain.UniversityClass;
import com.poli.meets.feedback.service.FeedbackService;
import com.poli.meets.feedback.service.dto.FeedbackSubjectDetailsDTO;
import com.poli.meets.feedback.service.dto.CategorySubjectsDTO;
import com.poli.meets.feedback.service.dto.SubjectDTO;
import com.poli.meets.feedback.service.UniversityClassService;

import com.poli.meets.feedback.service.dto.UniversityClassDTO;
import com.poli.meets.feedback.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/university-classes")
@AllArgsConstructor
public class UniversityClassResource {

    private final UniversityClassService universityClassService;

    private final FeedbackService feedbackService;


    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getUniverisityClass(@PathVariable Long id) {
        return ResponseEntity.ok(universityClassService.findOne(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<CategorySubjectsDTO>> getAllUniversityClassesForCurrentUser(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(universityClassService.findAllCategorySubjects(token));
    }

    @GetMapping("/{id}/feedback-details")
    public ResponseEntity<FeedbackSubjectDetailsDTO> getUniversityClassFeedbackDetails(
            @PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getUniversityClassFeedbackDetails(id));
    }

}
