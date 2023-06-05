package com.poli.meets.feedback.domain.enumeration;

/**
 * The Grade enumeration.
 */
public enum Grade {
    GRADE_5(5),
    GRADE_4(4),
    GRADE_3(3),
    GRADE_2(2),
    GRADE_1(1);

    private final Integer value;

    Grade(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
