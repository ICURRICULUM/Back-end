package icurriculum.domain.graduation.service.module.processor.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Category;
import java.util.List;
import java.util.Set;

public abstract class ProcessorResponse {

    public record SwAiDTO(
        int completedCredit,
        int requiredCredit,
        boolean isClear
    ) {
    }

    public record CreativityDTO(
        int completedCredit,
        int requiredCredit,
        boolean isClear
    ) {
    }

    public record CoreDTO(
        int completedCredit,
        int requiredCredit,
        Set<Category> uncompletedArea,
        boolean isClear
    ) {
    }

    public record GeneralRequiredDTO(
        int completedCredit,
        int requiredCredit,
        Set<Course> uncompletedCourseSet,
        boolean isClear
    ) {
    }

    public record MajorRequiredDTO(
        int completedCredit,
        int requiredCredit,
        List<Course> uncompletedCourseList,
        boolean isClear
    ) {
    }

    public record MajorSelectDTO(
        int totalMajorCompletedCredit,
        int totalMajorRequiredCredit,
        boolean isClear
    ) {
    }


}
