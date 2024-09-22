package icurriculum.global.util;

import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class TakeUtils {

    public static List<Take> getCoreTakes(List<Take> takes) {
        return takes.stream()
            .filter(t -> isCoreCategory(t.getCategory()))
            .collect(Collectors.toList());
    }

    public static List<Take> getTakesByCategory(List<Take> takes, Category category) {
        return takes.stream()
            .filter(t -> t.getCategory() == category)
            .collect(Collectors.toList());
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

    public static boolean isTakenOrAlternativeTaken(
        String targetCourseCode,
        Set<String> alternativeCodes,
        Set<String> takenCodes
    ) {
        if (takenCodes.contains(targetCourseCode)) {
            return true;
        }

        if (alternativeCodes == null) {
            return false;
        }

        for (String altCode : alternativeCodes) {
            if (takenCodes.contains(altCode)) {
                return true;
            }
        }

        return false;
    }

    public static List<Take> getUniqueTakes(List<Take> takes) {
        Set<Take> uniqueTakes = new HashSet<>(takes);
        return new ArrayList<>(uniqueTakes);
    }

    /**
     * 핵심교양 대체과목 포함시키는 method
     */
    public static void addCoreAlternativeTakesByCategory(Category category,
        Map<Category, Set<String>> alternativeCodesByArea, List<Take> targetTakes,
        List<Take> allTakes) {
        Set<String> alternativeCodes = alternativeCodesByArea.get(category);
        if (alternativeCodes == null) {
            return;
        }

        for (Take take : allTakes) {
            if (alternativeCodes.contains(take.getEffectiveCourse().getCode())) {
                targetTakes.add(take);
            }
        }
    }

    /**
     * 대체과목 targetTakes 에 추가시키는 method
     */
    public static void addAlternativeTakes(
        Category category,
        Set<String> alternativeCodeSetByCategory,
        List<Take> targetTakes,
        List<Take> allTakes
    ) {
        if (alternativeCodeSetByCategory.isEmpty()) {
            return;
        }

        for (Take take : allTakes) {
            if (alternativeCodeSetByCategory.contains(take.getEffectiveCourse().getCode())) {
                targetTakes.add(take);
            }
        }
    }

    /**
     * Group 에 속한 수업인데, 누락시 targetTakes 에 추가시키는 메소드
     */
    public static void addTakesWithGroup(
        Category category,
        Set<String> groupCodeSet,
        Map<String, Set<String>> alternativeCodesMap,
        List<Take> targetTakes,
        List<Take> allTakes
    ) {
        Set<String> codes = TakeUtils.extractCodes(allTakes);

        for (Take take : allTakes) {
            for (String code : groupCodeSet) {
                if (isTakenOrAlternativeTaken(code, alternativeCodesMap.get(code), codes)) {
                    targetTakes.add(take);
                }
            }
        }
    }

    public static void addTakesMissing(
        Category category,
        Set<String> codeSet,
        Map<String, Set<String>> alternativeCodesMap,
        List<Take> targetTakes,
        List<Take> allTakes
    ) {
        Set<String> codes = TakeUtils.extractCodes(allTakes);

        for (Take take : allTakes) {
            for (String code : codeSet) {
                if (isTakenOrAlternativeTaken(code, alternativeCodesMap.get(code), codes)) {
                    targetTakes.add(take);
                }
            }
        }
    }

    private static boolean isCoreCategory(Category category) {
        return category == Category.핵심교양1 || category == Category.핵심교양2 ||
            category == Category.핵심교양3 || category == Category.핵심교양4 ||
            category == Category.핵심교양5 || category == Category.핵심교양6;
    }

}
