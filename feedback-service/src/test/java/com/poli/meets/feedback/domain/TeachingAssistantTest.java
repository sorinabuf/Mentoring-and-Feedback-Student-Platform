package com.poli.meets.feedback.domain;

import com.poli.meets.feedback.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TeachingAssistantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachingAssistant.class);
        TeachingAssistant teachingAssistant1 = new TeachingAssistant();
        teachingAssistant1.setId(1L);
        TeachingAssistant teachingAssistant2 = new TeachingAssistant();
        teachingAssistant2.setId(teachingAssistant1.getId());
        assertThat(teachingAssistant1).isEqualTo(teachingAssistant2);
        teachingAssistant2.setId(2L);
        assertThat(teachingAssistant1).isNotEqualTo(teachingAssistant2);
        teachingAssistant1.setId(null);
        assertThat(teachingAssistant1).isNotEqualTo(teachingAssistant2);
    }
}
