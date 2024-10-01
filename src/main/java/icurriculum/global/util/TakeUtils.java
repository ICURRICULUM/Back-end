package icurriculum.global.util;

import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.List;

public abstract class TakeUtils {

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

}
