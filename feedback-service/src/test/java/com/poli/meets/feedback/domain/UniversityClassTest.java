package com.poli.meets.feedback.domain;

import com.poli.meets.feedback.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UniversityClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniversityClass.class);
        UniversityClass universityClass1 = new UniversityClass();
        universityClass1.setId(1L);
        UniversityClass universityClass2 = new UniversityClass();
        universityClass2.setId(universityClass1.getId());
        assertThat(universityClass1).isEqualTo(universityClass2);
        universityClass2.setId(2L);
        assertThat(universityClass1).isNotEqualTo(universityClass2);
        universityClass1.setId(null);
        assertThat(universityClass1).isNotEqualTo(universityClass2);
    }
}
