package com.poli.meets.feedback.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.poli.meets.feedback.web.rest.TestUtil;

public class FacultyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacultyDTO.class);
        FacultyDTO facultyDTO1 = new FacultyDTO();
        facultyDTO1.setId(1L);
        FacultyDTO facultyDTO2 = new FacultyDTO();
        assertThat(facultyDTO1).isNotEqualTo(facultyDTO2);
        facultyDTO2.setId(facultyDTO1.getId());
        assertThat(facultyDTO1).isEqualTo(facultyDTO2);
        facultyDTO2.setId(2L);
        assertThat(facultyDTO1).isNotEqualTo(facultyDTO2);
        facultyDTO1.setId(null);
        assertThat(facultyDTO1).isNotEqualTo(facultyDTO2);
    }
}
