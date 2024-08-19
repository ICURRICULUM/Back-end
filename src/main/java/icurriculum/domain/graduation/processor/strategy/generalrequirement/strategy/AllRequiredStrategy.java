package icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.util.CourseUtils;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.GeneralRequirementDto;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.util.TakeUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static icurriculum.domain.converter.TakeToCourseConverter.convertTakesToCourseSet;
import static icurriculum.domain.course.util.CourseUtils.isTakenOrAlternativeTaken;

public class AllRequiredStrategy implements GeneralRequirementStrategy {
    @Override
    public GeneralRequirementDto execute(List<Course> generalRequirementCourses, List<Take> takes) {
        int completedCredit = TakeUtils.calculateTotalCredit(takes);
        int requiredCredits = CourseUtils.calculateTotalCredit(generalRequirementCourses);
        List<Course> uncompletedCourses = getUncompletedCourses(generalRequirementCourses, takes);
        boolean isClear = uncompletedCourses.isEmpty();

        return new GeneralRequirementDto(completedCredit, requiredCredits, uncompletedCourses, isClear);
    }

    private List<Course> getUncompletedCourses(List<Course> generalRequirementCourses, List<Take> takes) {
        Set<Course> takenCourses = convertTakesToCourseSet(takes);

        /**
         * 필수 과목 중 수강하지 않은 과목 List 반환
         */
        return generalRequirementCourses.stream()
                .filter(course -> !isTakenOrAlternativeTaken(course, takenCourses))
                .collect(Collectors.toList());
    }

}




