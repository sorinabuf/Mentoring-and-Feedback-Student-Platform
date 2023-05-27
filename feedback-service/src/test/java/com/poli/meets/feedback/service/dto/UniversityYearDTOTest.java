package com.poli.meets.feedback.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.poli.meets.feedback.web.rest.TestUtil;

public class UniversityYearDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniversityYearDTO.class);
        UniversityYearDTO universityYearDTO1 = new UniversityYearDTO();
        universityYearDTO1.setId(1L);
        UniversityYearDTO universityYearDTO2 = new UniversityYearDTO();
        assertThat(universityYearDTO1).isNotEqualTo(universityYearDTO2);
        universityYearDTO2.setId(universityYearDTO1.getId());
        assertThat(universityYearDTO1).isEqualTo(universityYearDTO2);
        universityYearDTO2.setId(2L);
        assertThat(universityYearDTO1).isNotEqualTo(universityYearDTO2);
        universityYearDTO1.setId(null);
        assertThat(universityYearDTO1).isNotEqualTo(universityYearDTO2);
    }
}
