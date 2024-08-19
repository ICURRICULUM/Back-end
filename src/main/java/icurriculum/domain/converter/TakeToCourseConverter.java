package icurriculum.domain.converter;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Take;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TakeToCourseConverter {

    /**
     * 수강과목을 Take -> Course Set
     */
    public static Set<Course> convertTakesToCourseSet(List<Take> takes) {
        return takes.stream()
                .map(Take::getCourse)
                .collect(Collectors.toSet());
    }
}
