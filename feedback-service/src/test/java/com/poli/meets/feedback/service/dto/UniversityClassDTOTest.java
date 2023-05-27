package com.poli.meets.feedback.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.poli.meets.feedback.web.rest.TestUtil;

public class UniversityClassDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniversityClassDTO.class);
        UniversityClassDTO universityClassDTO1 = new UniversityClassDTO();
        universityClassDTO1.setId(1L);
        UniversityClassDTO universityClassDTO2 = new UniversityClassDTO();
        assertThat(universityClassDTO1).isNotEqualTo(universityClassDTO2);
        universityClassDTO2.setId(universityClassDTO1.getId());
        assertThat(universityClassDTO1).isEqualTo(universityClassDTO2);
        universityClassDTO2.setId(2L);
        assertThat(universityClassDTO1).isNotEqualTo(universityClassDTO2);
        universityClassDTO1.setId(null);
        assertThat(universityClassDTO1).isNotEqualTo(universityClassDTO2);
    }
}
