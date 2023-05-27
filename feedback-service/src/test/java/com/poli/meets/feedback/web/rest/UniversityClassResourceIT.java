package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.FeedbackServiceApp;
import com.poli.meets.feedback.service.mapper.UniversityClassMapper;
import com.poli.meets.feedback.domain.UniversityClass;
import com.poli.meets.feedback.repository.UniversityClassRepository;
import com.poli.meets.feedback.service.UniversityClassService;
import com.poli.meets.feedback.service.dto.UniversityClassDTO;

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

import com.poli.meets.feedback.domain.enumeration.Semester;
/**
 * Integration tests for the {@link UniversityClassResource} REST controller.
 */
@SpringBootTest(classes = FeedbackServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UniversityClassResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Semester DEFAULT_SEMESTER = Semester.FIRST_SEMESTER;
    private static final Semester UPDATED_SEMESTER = Semester.SECOND_SEMESTER;

    @Autowired
    private UniversityClassRepository universityClassRepository;

    @Autowired
    private UniversityClassMapper universityClassMapper;

    @Autowired
    private UniversityClassService universityClassService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniversityClassMockMvc;

    private UniversityClass universityClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniversityClass createEntity(EntityManager em) {
        UniversityClass universityClass = new UniversityClass()
            .name(DEFAULT_NAME)
            .abbreviation(DEFAULT_ABBREVIATION)
            .description(DEFAULT_DESCRIPTION)
            .semester(DEFAULT_SEMESTER);
        return universityClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniversityClass createUpdatedEntity(EntityManager em) {
        UniversityClass universityClass = new UniversityClass()
            .name(UPDATED_NAME)
            .abbreviation(UPDATED_ABBREVIATION)
            .description(UPDATED_DESCRIPTION)
            .semester(UPDATED_SEMESTER);
        return universityClass;
    }

    @BeforeEach
    public void initTest() {
        universityClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniversityClass() throws Exception {
        int databaseSizeBeforeCreate = universityClassRepository.findAll().size();
        // Create the UniversityClass
        UniversityClassDTO universityClassDTO = universityClassMapper.toDto(universityClass);
        restUniversityClassMockMvc.perform(post("/api/university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityClassDTO)))
            .andExpect(status().isCreated());

        // Validate the UniversityClass in the database
        List<UniversityClass> universityClassList = universityClassRepository.findAll();
        assertThat(universityClassList).hasSize(databaseSizeBeforeCreate + 1);
        UniversityClass testUniversityClass = universityClassList.get(universityClassList.size() - 1);
        assertThat(testUniversityClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUniversityClass.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
        assertThat(testUniversityClass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUniversityClass.getSemester()).isEqualTo(DEFAULT_SEMESTER);
    }

    @Test
    @Transactional
    public void createUniversityClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = universityClassRepository.findAll().size();

        // Create the UniversityClass with an existing ID
        universityClass.setId(1L);
        UniversityClassDTO universityClassDTO = universityClassMapper.toDto(universityClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversityClassMockMvc.perform(post("/api/university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniversityClass in the database
        List<UniversityClass> universityClassList = universityClassRepository.findAll();
        assertThat(universityClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUniversityClasses() throws Exception {
        // Initialize the database
        universityClassRepository.saveAndFlush(universityClass);

        // Get all the universityClassList
        restUniversityClassMockMvc.perform(get("/api/university-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universityClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())));
    }
    
    @Test
    @Transactional
    public void getUniversityClass() throws Exception {
        // Initialize the database
        universityClassRepository.saveAndFlush(universityClass);

        // Get the universityClass
        restUniversityClassMockMvc.perform(get("/api/university-classes/{id}", universityClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(universityClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUniversityClass() throws Exception {
        // Get the universityClass
        restUniversityClassMockMvc.perform(get("/api/university-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniversityClass() throws Exception {
        // Initialize the database
        universityClassRepository.saveAndFlush(universityClass);

        int databaseSizeBeforeUpdate = universityClassRepository.findAll().size();

        // Update the universityClass
        UniversityClass updatedUniversityClass = universityClassRepository.findById(universityClass.getId()).get();
        // Disconnect from session so that the updates on updatedUniversityClass are not directly saved in db
        em.detach(updatedUniversityClass);
        updatedUniversityClass
            .name(UPDATED_NAME)
            .abbreviation(UPDATED_ABBREVIATION)
            .description(UPDATED_DESCRIPTION)
            .semester(UPDATED_SEMESTER);
        UniversityClassDTO universityClassDTO = universityClassMapper.toDto(updatedUniversityClass);

        restUniversityClassMockMvc.perform(put("/api/university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityClassDTO)))
            .andExpect(status().isOk());

        // Validate the UniversityClass in the database
        List<UniversityClass> universityClassList = universityClassRepository.findAll();
        assertThat(universityClassList).hasSize(databaseSizeBeforeUpdate);
        UniversityClass testUniversityClass = universityClassList.get(universityClassList.size() - 1);
        assertThat(testUniversityClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversityClass.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
        assertThat(testUniversityClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUniversityClass.getSemester()).isEqualTo(UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    public void updateNonExistingUniversityClass() throws Exception {
        int databaseSizeBeforeUpdate = universityClassRepository.findAll().size();

        // Create the UniversityClass
        UniversityClassDTO universityClassDTO = universityClassMapper.toDto(universityClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversityClassMockMvc.perform(put("/api/university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniversityClass in the database
        List<UniversityClass> universityClassList = universityClassRepository.findAll();
        assertThat(universityClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUniversityClass() throws Exception {
        // Initialize the database
        universityClassRepository.saveAndFlush(universityClass);

        int databaseSizeBeforeDelete = universityClassRepository.findAll().size();

        // Delete the universityClass
        restUniversityClassMockMvc.perform(delete("/api/university-classes/{id}", universityClass.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UniversityClass> universityClassList = universityClassRepository.findAll();
        assertThat(universityClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
