package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.service.dto.FeedbackCountDTO;
import com.poli.meets.feedback.service.dto.FeedbackPostDTO;
import com.poli.meets.feedback.service.FeedbackService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FeedbackResource {

    private final FeedbackService feedbackService;


    @PostMapping("/feedbacks")
    public ResponseEntity<FeedbackPostDTO> createFeedback(@RequestHeader("Authorization") String token,
                                                          @RequestBody FeedbackPostDTO feedbackPostDTO) throws URISyntaxException {

        FeedbackPostDTO result = feedbackService.save(token, feedbackPostDTO);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId()))
            .body(result);
    }


    @GetMapping("/feedbacks")
    public List<FeedbackPostDTO> getAllFeedbacks() {
        return feedbackService.findAll();
    }


    @GetMapping("/feedbacks/count")
    public ResponseEntity<FeedbackCountDTO> getFeedbackCount(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(feedbackService.getFeedbackCount(token));
    }
}
