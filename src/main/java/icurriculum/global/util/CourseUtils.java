package icurriculum.global.util;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.List;

public abstract class CourseUtils {

    public static int calculateTotalCredit(List<Course> courses) {
        return courses.stream()
            .mapToInt(Course::getCredit)
            .sum();
    }

    public static int calculateTotalCredit(LinkedList<Take> allTakeList) {
        return allTakeList.stream()
            .mapToInt(t -> t.getEffectiveCourse().getCredit()).sum();
    }

}
