package com.poli.meets.feedback.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.poli.meets.feedback.web.rest.TestUtil;

public class FeedbackDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackDTO.class);
        FeedbackDTO feedbackDTO1 = new FeedbackDTO();
        feedbackDTO1.setId(1L);
        FeedbackDTO feedbackDTO2 = new FeedbackDTO();
        assertThat(feedbackDTO1).isNotEqualTo(feedbackDTO2);
        feedbackDTO2.setId(feedbackDTO1.getId());
        assertThat(feedbackDTO1).isEqualTo(feedbackDTO2);
        feedbackDTO2.setId(2L);
        assertThat(feedbackDTO1).isNotEqualTo(feedbackDTO2);
        feedbackDTO1.setId(null);
        assertThat(feedbackDTO1).isNotEqualTo(feedbackDTO2);
    }
}
