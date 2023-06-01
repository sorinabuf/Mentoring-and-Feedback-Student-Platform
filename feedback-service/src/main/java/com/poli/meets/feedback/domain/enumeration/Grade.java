package com.poli.meets.feedback.domain.enumeration;

/**
 * The Grade enumeration.
 */
public enum Grade {
    STRONGLY_AGREE(5),
    AGREE(4),
    NEUTRAL(3),
    DISAGREE(2),
    STRONGLY_DISAGREE(1);

    private final Integer value;

    Grade(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
