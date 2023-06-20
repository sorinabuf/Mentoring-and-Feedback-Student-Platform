package poli.meets.coreservice.service.dto;

import com.poli.meets.feedback.domain.enumeration.Semester;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;


@Data
@NoArgsConstructor
public class UniversityClassDTO implements Serializable {
    
    private Long id;

    private String name;

    private String abbreviation;

    private String description;

    @Enumerated(EnumType.STRING)
    private Semester semester;

    private TeacherDTO teacher;

    private UniversityYearDTO universityYear;
}
