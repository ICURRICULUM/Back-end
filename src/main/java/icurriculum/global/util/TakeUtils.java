package icurriculum.global.util;

import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.List;

public abstract class TakeUtils {

    private static final String CUSTOM = "CUSTOM";

    public static int calculateTotalCredit(LinkedList<Take> allTakeList) {
        return allTakeList.stream()
            .mapToInt(t -> t.getEffectiveCourse().getCredit())
            .sum();
    }

    public static int calculateTotalCredit(List<Take> allTakeList) {
        return allTakeList.stream()
            .mapToInt(t -> t.getEffectiveCourse().getCredit())
            .sum();
    }

    public static boolean isCustomCode(String code) {
        return code.equals(CUSTOM);
    }

}
