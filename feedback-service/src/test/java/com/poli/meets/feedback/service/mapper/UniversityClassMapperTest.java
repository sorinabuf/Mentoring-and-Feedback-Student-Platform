package com.poli.meets.feedback.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UniversityClassMapperTest {

    private UniversityClassMapper universityClassMapper;

    @BeforeEach
    public void setUp() {
        universityClassMapper = new UniversityClassMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(universityClassMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(universityClassMapper.fromId(null)).isNull();
    }
}
