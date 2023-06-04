package com.poli.meets.feedback.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poli.meets.feedback.domain.enumeration.Grade;
import com.poli.meets.feedback.domain.enumeration.GradeDifficulty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * A Feedback.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedbackSequenceGenerator")
    @SequenceGenerator(
        name = "feedbackSequenceGenerator",
        sequenceName = "feedback_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_course")
    private Grade gradeCourse;

    @Column(name = "feedback_course")
    private String feedbackCourse;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_laboratory")
    private Grade gradeLaboratory;

    @Column(name = "feedback_laboratory")
    private String feedbackLaboratory;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_homework")
    private Grade gradeHomework;

    @Column(name = "feedback_during_semester")
    private String feedbackDuringSemester;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_exam")
    private Grade gradeExam;

    @Column(name = "feedback_exam")
    private String feedbackExam;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_difficulty")
    private GradeDifficulty gradeDifficulty;

    @Column(name = "feedback_difficulty")
    private String feedbackDifficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_relevance")
    private Grade gradeRelevance;

    @Column(name = "feedback_relevance")
    private String feedbackRelevance;

    @Column(name = "feedback_date")
    private LocalDateTime feedbackDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "feedbacks", allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(value = "feedbacks", allowSetters = true)
    private UniversityClass universityClass;

    @ManyToOne
    @JsonIgnoreProperties(value = "feedbacks", allowSetters = true)
    private TeachingAssistant teachingAssistant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Feedback id(Long id) {
        this.id = id;
        return this;
    }

    public Feedback gradeCourse(Grade gradeCourse) {
        this.gradeCourse = gradeCourse;
        return this;
    }

    public Feedback feedbackCourse(String feedbackCourse) {
        this.feedbackCourse = feedbackCourse;
        return this;
    }

    public Feedback gradeLaboratory(Grade gradeLaboratory) {
        this.gradeLaboratory = gradeLaboratory;
        return this;
    }

    public Feedback feedbackLaboratory(String feedbackLaboratory) {
        this.feedbackLaboratory = feedbackLaboratory;
        return this;
    }

    public Feedback gradeHomework(Grade gradeHomework) {
        this.gradeHomework = gradeHomework;
        return this;
    }

    public Feedback feedbackDuringSemester(String feedbackDuringSemester) {
        this.feedbackDuringSemester = feedbackDuringSemester;
        return this;
    }

    public Feedback gradeExam(Grade gradeExam) {
        this.gradeExam = gradeExam;
        return this;
    }

    public Feedback feedbackExam(String feedbackExam) {
        this.feedbackExam = feedbackExam;
        return this;
    }

    public Feedback gradeDifficulty(GradeDifficulty gradeDifficulty) {
        this.gradeDifficulty = gradeDifficulty;
        return this;
    }

    public Feedback feedbackDifficutly(String feedbackDiffculty) {
        this.feedbackDifficulty = feedbackDiffculty;
        return this;
    }

    public Feedback gradeRelevance(Grade gradeRelevance) {
        this.gradeRelevance = gradeRelevance;
        return this;
    }

    public Feedback feedbackRelevance(String feedbackRelevance) {
        this.feedbackRelevance = feedbackRelevance;
        return this;
    }

    public Feedback student(Student student) {
        this.student = student;
        return this;
    }

    public Feedback universityClass(UniversityClass universityClass) {
        this.universityClass = universityClass;
        return this;
    }

    public Feedback teachingAssistant(TeachingAssistant teachingAssistant) {
        this.teachingAssistant = teachingAssistant;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
