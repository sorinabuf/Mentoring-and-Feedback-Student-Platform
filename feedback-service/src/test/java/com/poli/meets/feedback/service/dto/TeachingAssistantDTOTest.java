package com.poli.meets.feedback.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.poli.meets.feedback.web.rest.TestUtil;

public class TeachingAssistantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachingAssistantDTO.class);
        TeachingAssistantDTO teachingAssistantDTO1 = new TeachingAssistantDTO();
        teachingAssistantDTO1.setId(1L);
        TeachingAssistantDTO teachingAssistantDTO2 = new TeachingAssistantDTO();
        assertThat(teachingAssistantDTO1).isNotEqualTo(teachingAssistantDTO2);
        teachingAssistantDTO2.setId(teachingAssistantDTO1.getId());
        assertThat(teachingAssistantDTO1).isEqualTo(teachingAssistantDTO2);
        teachingAssistantDTO2.setId(2L);
        assertThat(teachingAssistantDTO1).isNotEqualTo(teachingAssistantDTO2);
        teachingAssistantDTO1.setId(null);
        assertThat(teachingAssistantDTO1).isNotEqualTo(teachingAssistantDTO2);
    }
}
