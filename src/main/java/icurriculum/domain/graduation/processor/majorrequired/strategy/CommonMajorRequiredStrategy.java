package icurriculum.domain.graduation.processor.majorrequired.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.CourseUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonMajorRequiredStrategy implements MajorRequiredStrategy {

    private final ThreadLocal<Integer> completedCredit = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Set<Course>> completedCourseSet = ThreadLocal.withInitial(
        HashSet::new);

    @Override
    public ProcessorResponse.MajorRequiredDTO execute(
        ProcessorRequest.MajorRequiredDTO request,
        LinkedList<Take> allTakeList
    ) {
        List<Course> majorRequiredCourseList = request.majorRequiredCourseList();
        Map<String, Set<String>> alternativeCourseMap = request.alternativeCourseMap();

        for (Course essentialCourse : majorRequiredCourseList) {
            checkEssentialCourseTaken(essentialCourse, allTakeList, alternativeCourseMap);
        }

        int requiredCredit = CourseUtils.calculateTotalCredit(majorRequiredCourseList);
        List<Course> uncompletedCourses = getUncompletedCourseList(majorRequiredCourseList);

        return new ProcessorResponse.MajorRequiredDTO(
            completedCredit.get(),
            requiredCredit,
            uncompletedCourses,
            uncompletedCourses.isEmpty()
        );

    }

    private void checkEssentialCourseTaken(
        Course essentialCourse,
        LinkedList<Take> allTakeList,
        Map<String, Set<String>> alternativeCourseMap
    ) {
        Iterator<Take> iterator = allTakeList.iterator();

        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (checkIfEssentialCourseTaken(essentialCourse, take, iterator,
                alternativeCourseMap)) {
                return;
            }
        }
    }

    private boolean checkIfEssentialCourseTaken(
        Course essentialCourse,
        Take take,
        Iterator<Take> iterator,
        Map<String, Set<String>> alternativeCourseMap
    ) {
        String essentialCourseCode = essentialCourse.getCode();
        String takenCode = take.getEffectiveCourse().getCode();

        if (takenCode.equals(essentialCourseCode)) {
            updateTakeAndFieldStatus(essentialCourse, take, iterator);
            return true;
        }

        Set<String> alternativeCourseSet = alternativeCourseMap.get(essentialCourseCode);
        if (alternativeCourseSet != null && alternativeCourseSet.contains(takenCode)) {
            updateTakeAndFieldStatus(essentialCourse, take, iterator);
            return true;
        }

        return false;
    }

    private void updateTakeAndFieldStatus(
        Course essentialCourse,
        Take take,
        Iterator<Take> iterator
    ) {
        if (take.getCategory() != Category.전공필수) {
            log.error("전공필수로 인정된 과목이지만 category 가 전공필수이 아닙니다:{}", take);
            throw new GeneralException(ErrorStatus.CATEGORY_IS_NOT_VALID, take);
        }

        completedCredit.set(
            completedCredit.get() + take.getEffectiveCourse().getCredit()
        );
        completedCourseSet.get().add(essentialCourse);
        iterator.remove();
    }

    private List<Course> getUncompletedCourseList(List<Course> majorRequiredCourseList) {
        return majorRequiredCourseList.stream()
            .filter(course -> !completedCourseSet.get().contains(course))
            .collect(Collectors.toList());
    }

}




