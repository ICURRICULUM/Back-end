package icurriculum.domain.graduation.service.module.processor.core.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.graduation.service.module.processor.core.CoreResult;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorConverter;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.util.GraduationUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonCoreStrategy implements CoreStrategy {

    /*
     * [logic]
     * 1. 영역 대체 과목 화인
     *      영역 대체의 경우, Take LinkedList에서 삭제되지 않고, response 계산만 수행
     *      이유: 대체과목은 다른영역(ex.교양필수)에도 포함되기 때문에 교양필수에서 계산되어야 한다.
     * 2. take의 category를 기준으로 response 계산
     */

    @Override
    public ProcessorResponse.CoreDTO execute(
        ProcessorRequest.CoreDTO request,
        LinkedList<Take> allTakeList
    ) {
        CoreResult result = new CoreResult();
        result.initUncompletedArea(request.core());

        // 영역 대체 과목
        Set<Course> areaAltCourseSet = handleAreaAlternative(
            allTakeList,
            request.core(),
            request.alternativeCourse(),
            result
        );

        handleResult(
            request.core(),
            allTakeList,
            areaAltCourseSet,
            result
        );

        return ProcessorConverter.to(result);
    }


    /*
     * [영역 대체 logic]
     * 1. 영역 대체 과목은 take의 category와 관련 없이 response 계산 수행
     * 2. Take LinkedList 내에서 제외되지 않는다.
     * 3. return Set<Course>
            영역 대체된 과목을 담아 리턴한다.
     *      이 후 작업에서 Set에 담겨있는 과목이면 건너뛴다.
     */
    private Set<Course> handleAreaAlternative(
        LinkedList<Take> allTakeList,
        Core core,
        AlternativeCourse alternativeCourse,
        CoreResult result
    ) {
        Set<Course> areaAltCourseSet = new HashSet<>();

        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            Set<Category> requiredAreaSet = getRequiredAreaSetWhenHandleAreaAlt(core);
            for (Category area : requiredAreaSet) {
                Set<String> areaAlternativeCodeSet = core.getAreaAlternativeCodeSet(area);

                if (GraduationUtils.isApproved(take, areaAlternativeCodeSet)) {
                    result.update(take, iterator, area, true);
                    areaAltCourseSet.add(take.getEffectiveCourse());
                    continue;
                }

                if (GraduationUtils.isCodeAlternative(
                    take,
                    areaAlternativeCodeSet,
                    alternativeCourse)
                ) {
                    result.update(take, iterator, area, true);
                    areaAltCourseSet.add(take.getEffectiveCourse());
                }
            }
        }
        return areaAltCourseSet;
    }

    private Set<Category> getRequiredAreaSetWhenHandleAreaAlt(Core core) {
        if (!core.getIsAreaFixed()) {
            return GraduationUtils.CORE_CATEGORYSET;
        }
        return core.getRequiredAreaSet();
    }

    /*
     * [core logic]
     * - 영역 대체를 통해서 이미 계산된 과목은 건너뛴다.
     * - 핵심교양으로 인정되는 과목은 LinkedList에서 삭제하고, result 를 업데이트한다.
     */
    private void handleResult(
        Core core,
        LinkedList<Take> allTakeList,
        Set<Course> areaAltCourseSet,
        CoreResult result
    ) {
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (GraduationUtils.isAlreadyCheckedByAreaAlt(take, areaAltCourseSet)) {
                continue;
            }

            if (isCategoryApprovedToCore(take.getCategory(), core)) {
                result.update(take, iterator, take.getCategory(), false);
            }
        }

        result.setRequiredCredit(core);
        result.checkIsClear(core);
    }

    private boolean isCategoryApprovedToCore(Category category, Core core) {
        if (!core.getIsAreaFixed()) {
            return GraduationUtils.CORE_CATEGORYSET.contains(category);
        }
        return core.getRequiredAreaSet().contains(category);
    }

}
