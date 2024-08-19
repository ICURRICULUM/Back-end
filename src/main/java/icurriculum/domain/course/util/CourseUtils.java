package icurriculum.domain.course.util;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.alternative.AlternativeCourse;
import icurriculum.domain.department.DepartmentName;

import java.util.List;
import java.util.Set;

public class CourseUtils {
    public static int calculateTotalCredit(List<Course> courses) {
        return courses.stream()
                .mapToInt(Course::getCredit)
                .sum();
    }


    /**
     * 수강 확인 로직
     * 대체 과목 포함
     */
    public static boolean isTakenOrAlternativeTaken(Course course, Set<Course> takenCourses) {
        if (takenCourses.contains(course)) {
            return true;
        }

        /**
         * 없다면 대체 과목 확인
         */
        for (AlternativeCourse alternativeCourse : course.getAlternativeCourses()) {
            if (takenCourses.contains(alternativeCourse.getAlternative())) {
                return true;
            }
        }
        return false;
    }


}
