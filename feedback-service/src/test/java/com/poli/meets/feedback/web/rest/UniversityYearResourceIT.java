package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.FeedbackServiceApp;
import com.poli.meets.feedback.service.mapper.UniversityYearMapper;
import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.repository.UniversityYearRepository;
import com.poli.meets.feedback.service.UniversityYearService;
import com.poli.meets.feedback.service.dto.UniversityYearDTO;

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

import com.poli.meets.feedback.domain.enumeration.Year;
/**
 * Integration tests for the {@link UniversityYearResource} REST controller.
 */
@SpringBootTest(classes = FeedbackServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UniversityYearResourceIT {

    private static final Year DEFAULT_YEAR = Year.YEAR_I_DEGREE;
    private static final Year UPDATED_YEAR = Year.YEAR_II_DEGREE;

    private static final String DEFAULT_SERIES = "AAAAAAAAAA";
    private static final String UPDATED_SERIES = "BBBBBBBBBB";

    @Autowired
    private UniversityYearRepository universityYearRepository;

    @Autowired
    private UniversityYearMapper universityYearMapper;

    @Autowired
    private UniversityYearService universityYearService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniversityYearMockMvc;

    private UniversityYear universityYear;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniversityYear createEntity(EntityManager em) {
        UniversityYear universityYear = new UniversityYear()
            .year(DEFAULT_YEAR)
            .series(DEFAULT_SERIES);
        return universityYear;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniversityYear createUpdatedEntity(EntityManager em) {
        UniversityYear universityYear = new UniversityYear()
            .year(UPDATED_YEAR)
            .series(UPDATED_SERIES);
        return universityYear;
    }

    @BeforeEach
    public void initTest() {
        universityYear = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniversityYear() throws Exception {
        int databaseSizeBeforeCreate = universityYearRepository.findAll().size();
        // Create the UniversityYear
        UniversityYearDTO universityYearDTO = universityYearMapper.toDto(universityYear);
        restUniversityYearMockMvc.perform(post("/api/university-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityYearDTO)))
            .andExpect(status().isCreated());

        // Validate the UniversityYear in the database
        List<UniversityYear> universityYearList = universityYearRepository.findAll();
        assertThat(universityYearList).hasSize(databaseSizeBeforeCreate + 1);
        UniversityYear testUniversityYear = universityYearList.get(universityYearList.size() - 1);
        assertThat(testUniversityYear.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testUniversityYear.getSeries()).isEqualTo(DEFAULT_SERIES);
    }

    @Test
    @Transactional
    public void createUniversityYearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = universityYearRepository.findAll().size();

        // Create the UniversityYear with an existing ID
        universityYear.setId(1L);
        UniversityYearDTO universityYearDTO = universityYearMapper.toDto(universityYear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversityYearMockMvc.perform(post("/api/university-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniversityYear in the database
        List<UniversityYear> universityYearList = universityYearRepository.findAll();
        assertThat(universityYearList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUniversityYears() throws Exception {
        // Initialize the database
        universityYearRepository.saveAndFlush(universityYear);

        // Get all the universityYearList
        restUniversityYearMockMvc.perform(get("/api/university-years?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universityYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].series").value(hasItem(DEFAULT_SERIES)));
    }
    
    @Test
    @Transactional
    public void getUniversityYear() throws Exception {
        // Initialize the database
        universityYearRepository.saveAndFlush(universityYear);

        // Get the universityYear
        restUniversityYearMockMvc.perform(get("/api/university-years/{id}", universityYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(universityYear.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.series").value(DEFAULT_SERIES));
    }
    @Test
    @Transactional
    public void getNonExistingUniversityYear() throws Exception {
        // Get the universityYear
        restUniversityYearMockMvc.perform(get("/api/university-years/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniversityYear() throws Exception {
        // Initialize the database
        universityYearRepository.saveAndFlush(universityYear);

        int databaseSizeBeforeUpdate = universityYearRepository.findAll().size();

        // Update the universityYear
        UniversityYear updatedUniversityYear = universityYearRepository.findById(universityYear.getId()).get();
        // Disconnect from session so that the updates on updatedUniversityYear are not directly saved in db
        em.detach(updatedUniversityYear);
        updatedUniversityYear
            .year(UPDATED_YEAR)
            .series(UPDATED_SERIES);
        UniversityYearDTO universityYearDTO = universityYearMapper.toDto(updatedUniversityYear);

        restUniversityYearMockMvc.perform(put("/api/university-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityYearDTO)))
            .andExpect(status().isOk());

        // Validate the UniversityYear in the database
        List<UniversityYear> universityYearList = universityYearRepository.findAll();
        assertThat(universityYearList).hasSize(databaseSizeBeforeUpdate);
        UniversityYear testUniversityYear = universityYearList.get(universityYearList.size() - 1);
        assertThat(testUniversityYear.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testUniversityYear.getSeries()).isEqualTo(UPDATED_SERIES);
    }

    @Test
    @Transactional
    public void updateNonExistingUniversityYear() throws Exception {
        int databaseSizeBeforeUpdate = universityYearRepository.findAll().size();

        // Create the UniversityYear
        UniversityYearDTO universityYearDTO = universityYearMapper.toDto(universityYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversityYearMockMvc.perform(put("/api/university-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universityYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniversityYear in the database
        List<UniversityYear> universityYearList = universityYearRepository.findAll();
        assertThat(universityYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUniversityYear() throws Exception {
        // Initialize the database
        universityYearRepository.saveAndFlush(universityYear);

        int databaseSizeBeforeDelete = universityYearRepository.findAll().size();

        // Delete the universityYear
        restUniversityYearMockMvc.perform(delete("/api/university-years/{id}", universityYear.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UniversityYear> universityYearList = universityYearRepository.findAll();
        assertThat(universityYearList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
