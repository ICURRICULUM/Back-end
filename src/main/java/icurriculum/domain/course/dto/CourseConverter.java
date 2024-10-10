package icurriculum.domain.course.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.dto.CourseResponse;
import icurriculum.domain.take.Category;

public abstract class CourseConverter {
    public static CourseResponse.DetailInfoDTO toCourseDetailInfo(Course course, Category category) {
        return CourseResponse.DetailInfoDTO.builder()
                .courseId(course.getId())
                .name(course.getName())
                .credit(course.getCredit())
                .code(course.getCode())
                .category(category.toString())
                .build();
    }
}
