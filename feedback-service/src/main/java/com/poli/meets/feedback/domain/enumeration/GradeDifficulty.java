package com.poli.meets.feedback.domain.enumeration;

public enum GradeDifficulty {
    VERY_DIFFICULT(5),
    DIFFICULTY(4),
    MODERATE(3),
    EASY(2),
    VERY_EASY(1);

    private final Integer value;

    GradeDifficulty(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
