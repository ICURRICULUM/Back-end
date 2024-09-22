package icurriculum.domain.graduation.processor.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Category;
import java.util.List;
import java.util.Set;

public abstract class ProcessorResponse {

    /*
     * 이수학점, 기준학점, 조건충족 여부
     */
    public record CreativityDTO(
        int completedCredit,
        int requiredCredits,
        boolean isClear
    ) {

    }

    /*
     * 이수학점, 기준학점, 조건충족 여부
     */
    public record SwAiDTO(
        int completedCredit,
        int requiredCredits,
        boolean isClear
    ) {

    }

    /*
     * 이수학점, 기준학점, 미이수 영역, 조건충족 여부
     */
    public record CoreDTO(
        int completedCredit,
        int requiredCredits,
        Set<Category> uncompletedArea,
        boolean isClear
    ) {

    }

    /*
     * 이수학점, 기준학점, 미이수 과목, 조건충족 여부
     */
    public record GeneralRequiredDTO(
        int completedCredit,
        int requiredCredits,
        List<Course> uncompletedCourseList,
        boolean isClear
    ) {

    }

    /*
     * 이수학점, 기준학점, 미이수 과목, 조건충족 여부
     */
    public record MajorRequiredDTO(
        int completedCredit,
        int requiredCredits,
        List<Course> uncompletedCourseList,
        boolean isClear
    ) {

    }

    /*
     * 이수학점
     */
    public record MajorSelectDTO(
        int completedCredit
    ) {

    }
}
