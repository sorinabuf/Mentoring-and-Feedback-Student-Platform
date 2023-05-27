package com.poli.meets.feedback.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UniversityYearMapperTest {

    private UniversityYearMapper universityYearMapper;

    @BeforeEach
    public void setUp() {
        universityYearMapper = new UniversityYearMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(universityYearMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(universityYearMapper.fromId(null)).isNull();
    }
}
