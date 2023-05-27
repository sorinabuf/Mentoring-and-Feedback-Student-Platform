package com.poli.meets.feedback.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TeachingAssistantMapperTest {

    private TeachingAssistantMapper teachingAssistantMapper;

    @BeforeEach
    public void setUp() {
        teachingAssistantMapper = new TeachingAssistantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(teachingAssistantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(teachingAssistantMapper.fromId(null)).isNull();
    }
}
