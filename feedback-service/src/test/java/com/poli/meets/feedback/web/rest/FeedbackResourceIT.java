package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.FeedbackServiceApp;
import com.poli.meets.feedback.domain.Feedback;
import com.poli.meets.feedback.repository.FeedbackRepository;
import com.poli.meets.feedback.service.FeedbackService;
import com.poli.meets.feedback.service.dto.FeedbackDTO;
import com.poli.meets.feedback.service.mapper.FeedbackMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.poli.meets.feedback.domain.enumeration.Grade;

/**
 * Integration tests for the {@link FeedbackResource} REST controller.
 */
@SpringBootTest(classes = FeedbackServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeedbackResourceIT {

    private static final Grade DEFAULT_GRADE_COURSE = Grade.STRONGLY_AGREE;
    private static final Grade UPDATED_GRADE_COURSE = Grade.AGREE;

    private static final String DEFAULT_FEEDBACK_COURSE = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK_COURSE = "BBBBBBBBBB";

    private static final Grade DEFAULT_GRADE_LABORATORY = Grade.STRONGLY_AGREE;
    private static final Grade UPDATED_GRADE_LABORATORY = Grade.AGREE;

    private static final String DEFAULT_FEEDBACK_LABORATORY = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK_LABORATORY = "BBBBBBBBBB";

    private static final Grade DEFAULT_GRADE_HOMEWORK = Grade.STRONGLY_AGREE;
    private static final Grade UPDATED_GRADE_HOMEWORK = Grade.AGREE;

    private static final String DEFAULT_FEEDBACK_DURING_SEMESTER = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK_DURING_SEMESTER = "BBBBBBBBBB";

    private static final Grade DEFAULT_GRADE_EXAM = Grade.STRONGLY_AGREE;
    private static final Grade UPDATED_GRADE_EXAM = Grade.AGREE;

    private static final String DEFAULT_FEEDBACK_EXAM = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK_EXAM = "BBBBBBBBBB";

    private static final Grade DEFAULT_GRADE_DIFFICULTY = Grade.STRONGLY_AGREE;
    private static final Grade UPDATED_GRADE_DIFFICULTY = Grade.AGREE;

    private static final String DEFAULT_FEEDBACK_DIFFICUTLY = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK_DIFFICUTLY = "BBBBBBBBBB";

    private static final Grade DEFAULT_GRADE_RELEVANCE = Grade.STRONGLY_AGREE;
    private static final Grade UPDATED_GRADE_RELEVANCE = Grade.AGREE;

    private static final String DEFAULT_FEEDBACK_RELEVANCE = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK_RELEVANCE = "BBBBBBBBBB";

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .gradeCourse(DEFAULT_GRADE_COURSE)
            .feedbackCourse(DEFAULT_FEEDBACK_COURSE)
            .gradeLaboratory(DEFAULT_GRADE_LABORATORY)
            .feedbackLaboratory(DEFAULT_FEEDBACK_LABORATORY)
            .gradeHomework(DEFAULT_GRADE_HOMEWORK)
            .feedbackDuringSemester(DEFAULT_FEEDBACK_DURING_SEMESTER)
            .gradeExam(DEFAULT_GRADE_EXAM)
            .feedbackExam(DEFAULT_FEEDBACK_EXAM)
            .gradeDifficulty(DEFAULT_GRADE_DIFFICULTY)
            .feedbackDifficutly(DEFAULT_FEEDBACK_DIFFICUTLY)
            .gradeRelevance(DEFAULT_GRADE_RELEVANCE)
            .feedbackRelevance(DEFAULT_FEEDBACK_RELEVANCE);
        return feedback;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createUpdatedEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .gradeCourse(UPDATED_GRADE_COURSE)
            .feedbackCourse(UPDATED_FEEDBACK_COURSE)
            .gradeLaboratory(UPDATED_GRADE_LABORATORY)
            .feedbackLaboratory(UPDATED_FEEDBACK_LABORATORY)
            .gradeHomework(UPDATED_GRADE_HOMEWORK)
            .feedbackDuringSemester(UPDATED_FEEDBACK_DURING_SEMESTER)
            .gradeExam(UPDATED_GRADE_EXAM)
            .feedbackExam(UPDATED_FEEDBACK_EXAM)
            .gradeDifficulty(UPDATED_GRADE_DIFFICULTY)
            .feedbackDifficutly(UPDATED_FEEDBACK_DIFFICUTLY)
            .gradeRelevance(UPDATED_GRADE_RELEVANCE)
            .feedbackRelevance(UPDATED_FEEDBACK_RELEVANCE);
        return feedback;
    }

    @BeforeEach
    public void initTest() {
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();
        // Create the Feedback
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);
        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getGradeCourse()).isEqualTo(DEFAULT_GRADE_COURSE);
        assertThat(testFeedback.getFeedbackCourse()).isEqualTo(DEFAULT_FEEDBACK_COURSE);
        assertThat(testFeedback.getGradeLaboratory()).isEqualTo(DEFAULT_GRADE_LABORATORY);
        assertThat(testFeedback.getFeedbackLaboratory()).isEqualTo(DEFAULT_FEEDBACK_LABORATORY);
        assertThat(testFeedback.getGradeHomework()).isEqualTo(DEFAULT_GRADE_HOMEWORK);
        assertThat(testFeedback.getFeedbackDuringSemester()).isEqualTo(DEFAULT_FEEDBACK_DURING_SEMESTER);
        assertThat(testFeedback.getGradeExam()).isEqualTo(DEFAULT_GRADE_EXAM);
        assertThat(testFeedback.getFeedbackExam()).isEqualTo(DEFAULT_FEEDBACK_EXAM);
        assertThat(testFeedback.getGradeDifficulty()).isEqualTo(DEFAULT_GRADE_DIFFICULTY);
        assertThat(testFeedback.getFeedbackDifficutly()).isEqualTo(DEFAULT_FEEDBACK_DIFFICUTLY);
        assertThat(testFeedback.getGradeRelevance()).isEqualTo(DEFAULT_GRADE_RELEVANCE);
        assertThat(testFeedback.getFeedbackRelevance()).isEqualTo(DEFAULT_FEEDBACK_RELEVANCE);
    }

    @Test
    @Transactional
    public void createFeedbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback with an existing ID
        feedback.setId(1L);
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList
        restFeedbackMockMvc.perform(get("/api/feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].gradeCourse").value(hasItem(DEFAULT_GRADE_COURSE.toString())))
            .andExpect(jsonPath("$.[*].feedbackCourse").value(hasItem(DEFAULT_FEEDBACK_COURSE)))
            .andExpect(jsonPath("$.[*].gradeLaboratory").value(hasItem(DEFAULT_GRADE_LABORATORY.toString())))
            .andExpect(jsonPath("$.[*].feedbackLaboratory").value(hasItem(DEFAULT_FEEDBACK_LABORATORY)))
            .andExpect(jsonPath("$.[*].gradeHomework").value(hasItem(DEFAULT_GRADE_HOMEWORK.toString())))
            .andExpect(jsonPath("$.[*].feedbackDuringSemester").value(hasItem(DEFAULT_FEEDBACK_DURING_SEMESTER)))
            .andExpect(jsonPath("$.[*].gradeExam").value(hasItem(DEFAULT_GRADE_EXAM.toString())))
            .andExpect(jsonPath("$.[*].feedbackExam").value(hasItem(DEFAULT_FEEDBACK_EXAM)))
            .andExpect(jsonPath("$.[*].gradeDifficulty").value(hasItem(DEFAULT_GRADE_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].feedbackDifficutly").value(hasItem(DEFAULT_FEEDBACK_DIFFICUTLY)))
            .andExpect(jsonPath("$.[*].gradeRelevance").value(hasItem(DEFAULT_GRADE_RELEVANCE.toString())))
            .andExpect(jsonPath("$.[*].feedbackRelevance").value(hasItem(DEFAULT_FEEDBACK_RELEVANCE)));
    }
    
    @Test
    @Transactional
    public void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.gradeCourse").value(DEFAULT_GRADE_COURSE.toString()))
            .andExpect(jsonPath("$.feedbackCourse").value(DEFAULT_FEEDBACK_COURSE))
            .andExpect(jsonPath("$.gradeLaboratory").value(DEFAULT_GRADE_LABORATORY.toString()))
            .andExpect(jsonPath("$.feedbackLaboratory").value(DEFAULT_FEEDBACK_LABORATORY))
            .andExpect(jsonPath("$.gradeHomework").value(DEFAULT_GRADE_HOMEWORK.toString()))
            .andExpect(jsonPath("$.feedbackDuringSemester").value(DEFAULT_FEEDBACK_DURING_SEMESTER))
            .andExpect(jsonPath("$.gradeExam").value(DEFAULT_GRADE_EXAM.toString()))
            .andExpect(jsonPath("$.feedbackExam").value(DEFAULT_FEEDBACK_EXAM))
            .andExpect(jsonPath("$.gradeDifficulty").value(DEFAULT_GRADE_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.feedbackDifficutly").value(DEFAULT_FEEDBACK_DIFFICUTLY))
            .andExpect(jsonPath("$.gradeRelevance").value(DEFAULT_GRADE_RELEVANCE.toString()))
            .andExpect(jsonPath("$.feedbackRelevance").value(DEFAULT_FEEDBACK_RELEVANCE));
    }
    @Test
    @Transactional
    public void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findById(feedback.getId()).get();
        // Disconnect from session so that the updates on updatedFeedback are not directly saved in db
        em.detach(updatedFeedback);
        updatedFeedback
            .gradeCourse(UPDATED_GRADE_COURSE)
            .feedbackCourse(UPDATED_FEEDBACK_COURSE)
            .gradeLaboratory(UPDATED_GRADE_LABORATORY)
            .feedbackLaboratory(UPDATED_FEEDBACK_LABORATORY)
            .gradeHomework(UPDATED_GRADE_HOMEWORK)
            .feedbackDuringSemester(UPDATED_FEEDBACK_DURING_SEMESTER)
            .gradeExam(UPDATED_GRADE_EXAM)
            .feedbackExam(UPDATED_FEEDBACK_EXAM)
            .gradeDifficulty(UPDATED_GRADE_DIFFICULTY)
            .feedbackDifficutly(UPDATED_FEEDBACK_DIFFICUTLY)
            .gradeRelevance(UPDATED_GRADE_RELEVANCE)
            .feedbackRelevance(UPDATED_FEEDBACK_RELEVANCE);
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(updatedFeedback);

        restFeedbackMockMvc.perform(put("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getGradeCourse()).isEqualTo(UPDATED_GRADE_COURSE);
        assertThat(testFeedback.getFeedbackCourse()).isEqualTo(UPDATED_FEEDBACK_COURSE);
        assertThat(testFeedback.getGradeLaboratory()).isEqualTo(UPDATED_GRADE_LABORATORY);
        assertThat(testFeedback.getFeedbackLaboratory()).isEqualTo(UPDATED_FEEDBACK_LABORATORY);
        assertThat(testFeedback.getGradeHomework()).isEqualTo(UPDATED_GRADE_HOMEWORK);
        assertThat(testFeedback.getFeedbackDuringSemester()).isEqualTo(UPDATED_FEEDBACK_DURING_SEMESTER);
        assertThat(testFeedback.getGradeExam()).isEqualTo(UPDATED_GRADE_EXAM);
        assertThat(testFeedback.getFeedbackExam()).isEqualTo(UPDATED_FEEDBACK_EXAM);
        assertThat(testFeedback.getGradeDifficulty()).isEqualTo(UPDATED_GRADE_DIFFICULTY);
        assertThat(testFeedback.getFeedbackDifficutly()).isEqualTo(UPDATED_FEEDBACK_DIFFICUTLY);
        assertThat(testFeedback.getGradeRelevance()).isEqualTo(UPDATED_GRADE_RELEVANCE);
        assertThat(testFeedback.getFeedbackRelevance()).isEqualTo(UPDATED_FEEDBACK_RELEVANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Create the Feedback
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackMockMvc.perform(put("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Delete the feedback
        restFeedbackMockMvc.perform(delete("/api/feedbacks/{id}", feedback.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
