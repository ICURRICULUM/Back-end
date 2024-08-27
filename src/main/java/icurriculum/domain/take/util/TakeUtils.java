package icurriculum.domain.take.util;

import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;

import java.util.*;
import java.util.stream.Collectors;

public class TakeUtils {

    public static List<Take> getCoreTakes(List<Take> takes) {
        return takes.stream()
                .filter(t -> isCoreCategory(t.getCategory()))
                .collect(Collectors.toList());
    }

    public static List<Take> getTakesByCategory(List<Take> takes, Category category) {
        return takes.stream()
                .filter(t -> t.getCategory() == category)
                .toList();
    }

    public static int calculateTotalCredit(List<Take> takes) {
        return takes.stream()
                .mapToInt(take -> take.getEffectiveCourse().getCredit())
                .sum();
    }

    public static int calculateTotalCredit(Set<Take> takes) {
        return takes.stream()
                .mapToInt(take -> take.getEffectiveCourse().getCredit())
                .sum();
    }

    public static Set<String> extractCodes(List<Take> takes) {
        return takes.stream()
                .map(t -> t.getEffectiveCourse().getCode())
                .collect(Collectors.toSet());
    }

    public static boolean isTakenOrAlternativeTaken(String targetCourseCode, Set<String> alternativeCodes, Set<String> takenCodes) {
        if (takenCodes.contains(targetCourseCode)) {
            return true;
        }

        for (String altCode : alternativeCodes) {
            if (takenCodes.contains(altCode)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isCoreCategory(Category category) {
        return category == Category.핵심교양1 || category == Category.핵심교양2 ||
                category == Category.핵심교양3 || category == Category.핵심교양4 ||
                category == Category.핵심교양5 || category == Category.핵심교양6;
    }

}
