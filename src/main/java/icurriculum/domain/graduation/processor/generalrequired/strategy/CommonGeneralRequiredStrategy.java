package icurriculum.domain.graduation.processor.generalrequired.strategy;

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
public class CommonGeneralRequiredStrategy implements GeneralRequiredStrategy {

    private final ThreadLocal<Boolean> clearBasicEnglish = ThreadLocal.withInitial(() -> false);
    private final ThreadLocal<Boolean> clearAdvanceEnglish = ThreadLocal.withInitial(() -> false);


    private final ThreadLocal<Integer> completedCredit = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Set<Course>> completedCourseSet = ThreadLocal.withInitial(
        HashSet::new);

    @Override
    public ProcessorResponse.GeneralRequiredDTO execute(
        ProcessorRequest.GeneralRequiredDTO request,
        LinkedList<Take> allTakeList
    ) {
        List<Course> generalRequiredCourseList = request.courseListWithCurriculumData()
            .CourseList();
        Map<String, Set<String>> alternativeCourseCodeMap = request.alternativeCourseCodeMap();

        for (Course essentialCourse : generalRequiredCourseList) {
            checkEssentialCourseTaken(essentialCourse, allTakeList, alternativeCourseCodeMap);
        }

        int requiredCredit = calculateRequiredCredit(generalRequiredCourseList, request.joinYear());
        updateCompletedCourseSetAboutEnglish(request.joinYear());
        List<Course> uncompletedCourseList = getUncompletedCourseList(generalRequiredCourseList);

        return new ProcessorResponse.GeneralRequiredDTO(
            completedCredit.get(),
            requiredCredit,
            uncompletedCourseList,
            uncompletedCourseList.isEmpty()
        );
    }

    private void checkEssentialCourseTaken(
        Course essentialCourse,
        LinkedList<Take> allTakeList,
        Map<String, Set<String>> alternativeCourseCodeMap
    ) {
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (checkIfEssentialCourseTaken(essentialCourse, take, iterator,
                alternativeCourseCodeMap)) {
                return;
            }
        }
    }


    private boolean checkIfEssentialCourseTaken(
        Course essentialCourse,
        Take take,
        Iterator<Take> iterator,
        Map<String, Set<String>> alternativeCourseCodeMap
    ) {
        String essentialCourseCode = essentialCourse.getCode();
        String takenCode = take.getEffectiveCourse().getCode();

        if (takenCode.equals(essentialCourseCode)) {
            updateTakeAndFieldStatus(essentialCourse, take, iterator);
            return true;
        }

        Set<String> alternativeCourseSet = alternativeCourseCodeMap.get(essentialCourseCode);
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
        if (take.getCategory() != Category.교양필수) {
            log.error("교양필수으로 인정된 과목이지만 category 가 교양필수이 아닙니다:{}", take);
            throw new GeneralException(ErrorStatus.CATEGORY_IS_NOT_VALID, take);
        }

        completedCredit.set(
            completedCredit.get() + take.getEffectiveCourse().getCredit()
        );
        completedCourseSet.get().add(essentialCourse);
        updateEnglishClearStatus(essentialCourse.getCode());
        iterator.remove();
    }

    private void updateEnglishClearStatus(String essentialCourseCode) {
        if (essentialCourseCode.equals(ProcessorRequest.GeneralRequiredDTO.의사소통_영어.getCode()) ||
            essentialCourseCode.equals(ProcessorRequest.GeneralRequiredDTO.의사소통_영어_중급.getCode()) ||
            essentialCourseCode.equals(ProcessorRequest.GeneralRequiredDTO.의사소통_영어_고급.getCode())
        ) {
            clearBasicEnglish.set(true);
        }

        if (essentialCourseCode.equals(ProcessorRequest.GeneralRequiredDTO.실용영어_LS.getCode()) ||
            essentialCourseCode.equals(ProcessorRequest.GeneralRequiredDTO.실용영어_RW.getCode()) ||
            essentialCourseCode.equals(ProcessorRequest.GeneralRequiredDTO.고급대학영어.getCode())
        ) {
            clearAdvanceEnglish.set(true);
        }
    }

    private int calculateRequiredCredit(
        List<Course> generalRequiredCourseList,
        Integer joinYear
    ) {
        int totalCredit = CourseUtils.calculateTotalCredit(generalRequiredCourseList);

        if (ProcessorRequest.GeneralRequiredDTO.isCurriculumChanged(joinYear)) {
            return totalCredit - 6;
        }
        return totalCredit - 12;
    }

    private void updateCompletedCourseSetAboutEnglish(Integer joinYear) {
        if (clearBasicEnglish.get()) {
            completedCourseSet.get().add(ProcessorRequest.GeneralRequiredDTO.의사소통_영어);
            completedCourseSet.get().add(ProcessorRequest.GeneralRequiredDTO.의사소통_영어_중급);
            completedCourseSet.get().add(ProcessorRequest.GeneralRequiredDTO.의사소통_영어_고급);
        }

        if (!ProcessorRequest.GeneralRequiredDTO.isCurriculumChanged(joinYear)
            && clearAdvanceEnglish.get()) {
            completedCourseSet.get().add(ProcessorRequest.GeneralRequiredDTO.실용영어_LS);
            completedCourseSet.get().add(ProcessorRequest.GeneralRequiredDTO.실용영어_RW);
            completedCourseSet.get().add(ProcessorRequest.GeneralRequiredDTO.고급대학영어);
        }
    }

    private List<Course> getUncompletedCourseList(List<Course> generalRequiredCourseList) {
        return generalRequiredCourseList.stream()
            .filter(course -> !completedCourseSet.get().contains(course))
            .collect(Collectors.toList());
    }

}




