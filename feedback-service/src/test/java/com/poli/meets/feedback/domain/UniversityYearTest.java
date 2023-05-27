package com.poli.meets.feedback.domain;

import com.poli.meets.feedback.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UniversityYearTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniversityYear.class);
        UniversityYear universityYear1 = new UniversityYear();
        universityYear1.setId(1L);
        UniversityYear universityYear2 = new UniversityYear();
        universityYear2.setId(universityYear1.getId());
        assertThat(universityYear1).isEqualTo(universityYear2);
        universityYear2.setId(2L);
        assertThat(universityYear1).isNotEqualTo(universityYear2);
        universityYear1.setId(null);
        assertThat(universityYear1).isNotEqualTo(universityYear2);
    }
}
