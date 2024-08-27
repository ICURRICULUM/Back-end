package icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.util.CourseUtils;
import icurriculum.domain.curriculum.json.AlternativeCourseJson;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.GeneralRequirementDto;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.GeneralRequirement;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.util.TakeUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static icurriculum.domain.take.util.TakeUtils.extractCodes;
import static icurriculum.domain.take.util.TakeUtils.isTakenOrAlternativeTaken;

public class AllRequiredStrategy implements GeneralRequirementStrategy {

    @Override
    public GeneralRequirementDto execute(GeneralRequirement requirement, List<Take> takes) {
        int completedCredit = TakeUtils.calculateTotalCredit(takes);
        int requiredCredits = CourseUtils.calculateTotalCredit(requirement.generalRequirementCourses());
        List<Course> uncompletedCourses = getUncompletedCourses(requirement.generalRequirementCourses(), requirement.alternativeCourses(), takes);
        boolean isClear = uncompletedCourses.isEmpty();

        return new GeneralRequirementDto(completedCredit, requiredCredits, uncompletedCourses, isClear);
    }

    /**
     * 필수 과목 중 수강하지 않은 과목 List 반환
     */
    private List<Course> getUncompletedCourses(List<Course> generalRequirementCourses,
                                               AlternativeCourseJson alternativeCourses,
                                               List<Take> takes) {
        Set<String> takenCodeSet = extractCodes(takes);
        Map<String, Set<String>> alternativeCourseMap = alternativeCourses.getAlternativeCourseMap();

        return generalRequirementCourses.stream()
                .filter(
                        course -> !isTakenOrAlternativeTaken(
                                course.getCode(),
                                alternativeCourseMap.get(course.getCode()),
                                takenCodeSet)
                )
                .collect(Collectors.toList());
    }

}




