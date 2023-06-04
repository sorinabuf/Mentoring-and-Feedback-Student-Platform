package com.poli.meets.feedback.util;

import java.util.IntSummaryStatistics;
import java.util.List;

public class MathUtil {

    public static double getAverage(List<Integer> list) {
        IntSummaryStatistics stats = list.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        return stats.getAverage();
    }
}
