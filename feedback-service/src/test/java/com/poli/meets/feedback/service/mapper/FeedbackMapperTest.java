package com.poli.meets.feedback.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FeedbackMapperTest {

    private FeedbackMapper feedbackMapper;

    @BeforeEach
    public void setUp() {
        feedbackMapper = new FeedbackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(feedbackMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(feedbackMapper.fromId(null)).isNull();
    }
}
