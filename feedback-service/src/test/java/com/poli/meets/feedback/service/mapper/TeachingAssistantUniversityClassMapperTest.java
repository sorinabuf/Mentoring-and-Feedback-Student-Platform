package com.poli.meets.feedback.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TeachingAssistantUniversityClassMapperTest {

    private TeachingAssistantUniversityClassMapper teachingAssistantUniversityClassMapper;

    @BeforeEach
    public void setUp() {
        teachingAssistantUniversityClassMapper = new TeachingAssistantUniversityClassMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(teachingAssistantUniversityClassMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(teachingAssistantUniversityClassMapper.fromId(null)).isNull();
    }
}
