package icurriculum.domain.course.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Category;
import lombok.Builder;

@Builder
public class CourseSearchResponseDTO {
    private Long courseId;
    private String name;
    private Integer credit;
    private String category;
}
