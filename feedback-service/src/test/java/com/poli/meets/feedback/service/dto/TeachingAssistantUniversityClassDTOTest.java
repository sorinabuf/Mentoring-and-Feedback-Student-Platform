package com.poli.meets.feedback.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.poli.meets.feedback.web.rest.TestUtil;

public class TeachingAssistantUniversityClassDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachingAssistantUniversityClassDTO.class);
        TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO1 = new TeachingAssistantUniversityClassDTO();
        teachingAssistantUniversityClassDTO1.setId(1L);
        TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO2 = new TeachingAssistantUniversityClassDTO();
        assertThat(teachingAssistantUniversityClassDTO1).isNotEqualTo(teachingAssistantUniversityClassDTO2);
        teachingAssistantUniversityClassDTO2.setId(teachingAssistantUniversityClassDTO1.getId());
        assertThat(teachingAssistantUniversityClassDTO1).isEqualTo(teachingAssistantUniversityClassDTO2);
        teachingAssistantUniversityClassDTO2.setId(2L);
        assertThat(teachingAssistantUniversityClassDTO1).isNotEqualTo(teachingAssistantUniversityClassDTO2);
        teachingAssistantUniversityClassDTO1.setId(null);
        assertThat(teachingAssistantUniversityClassDTO1).isNotEqualTo(teachingAssistantUniversityClassDTO2);
    }
}
