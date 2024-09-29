package icurriculum.global.util;

import icurriculum.domain.course.Course;
import java.util.List;

public abstract class CourseUtils {

    public static int calculateTotalCredit(List<Course> courseList) {
        return courseList.stream()
            .mapToInt(Course::getCredit)
            .sum();
    }

}
