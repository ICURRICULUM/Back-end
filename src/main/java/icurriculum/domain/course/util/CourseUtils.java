package icurriculum.domain.course.util;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.CustomCourse;

import java.util.List;

public class CourseUtils {
    public static int calculateTotalCredit(List<Course> courses) {
        return courses.stream()
                .mapToInt(Course::getCredit)
                .sum();
    }

    public static Course convertCustomToCourse(CustomCourse customCourse) {
        return new Course(
                customCourse.getCode(),
                customCourse.getName(),
                customCourse.getCredit()
        );
    }

}
