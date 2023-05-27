package com.poli.meets.feedback.domain;

import com.poli.meets.feedback.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TeachingAssistantUniversityClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachingAssistantUniversityClass.class);
        TeachingAssistantUniversityClass teachingAssistantUniversityClass1 = new TeachingAssistantUniversityClass();
        teachingAssistantUniversityClass1.setId(1L);
        TeachingAssistantUniversityClass teachingAssistantUniversityClass2 = new TeachingAssistantUniversityClass();
        teachingAssistantUniversityClass2.setId(teachingAssistantUniversityClass1.getId());
        assertThat(teachingAssistantUniversityClass1).isEqualTo(teachingAssistantUniversityClass2);
        teachingAssistantUniversityClass2.setId(2L);
        assertThat(teachingAssistantUniversityClass1).isNotEqualTo(teachingAssistantUniversityClass2);
        teachingAssistantUniversityClass1.setId(null);
        assertThat(teachingAssistantUniversityClass1).isNotEqualTo(teachingAssistantUniversityClass2);
    }
}
