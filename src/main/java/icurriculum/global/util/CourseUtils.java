package icurriculum.global.util;

import icurriculum.domain.course.Course;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CourseUtils {

    public static int calculateTotalCredit(List<Course> courses) {
        return courses.stream()
            .mapToInt(Course::getCredit)
            .sum();
    }

    public static List<Course> separateCoursesByCodes(List<Course> remainCourseList,
        String... codes) {
        Set<String> codeSet = Set.of(codes);

        List<Course> separatedCourseList = remainCourseList.stream()
            .filter(course -> codeSet.contains(course.getCode()))
            .collect(Collectors.toList());

        remainCourseList.removeIf(course -> codeSet.contains(course.getCode()));

        return separatedCourseList;
    }

    public static Set<String> extractCodes(List<Course> courses) {
        return courses.stream()
            .map(Course::getCode)
            .collect(Collectors.toSet());
    }

}
