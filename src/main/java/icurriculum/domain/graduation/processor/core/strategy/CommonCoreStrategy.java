package icurriculum.domain.graduation.processor.core.strategy;

import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonCoreStrategy implements CoreStrategy {

    private final ThreadLocal<Integer> completedCredit = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Set<Category>> completedAreas = ThreadLocal.withInitial(HashSet::new);

    @Override
    public ProcessorResponse.CoreDTO execute(
        ProcessorRequest.CoreDTO request,
        LinkedList<Take> allTakeList
    ) {
        Boolean isAreaConfirmed = request.coreJson().getIsAreaConfirmed();
        if (!isAreaConfirmed) {
            return createWhenNotAreaConfirmed(request.coreJson(), allTakeList);
        }

        return createWhenAreaConfirmed(
            allTakeList,
            request.coreJson().getAlternativeCodesByArea(),
            request.alternativeCourseMap(),
            request.coreJson().getRequiredAreas(),
            request.coreJson().getRequiredCredit()
        );
    }

    /*
     * [영역 상관 없을 때]
     * - 핵심교양 과목이 아니면 건너뛴다.
     */
    private ProcessorResponse.CoreDTO createWhenNotAreaConfirmed(
        CoreJson coreJson,
        LinkedList<Take> allTakeList
    ) {
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();
            if (!isCore(take.getCategory(), coreJson.getRequiredAreas())) {
                continue;
            }
            updateStatus(take, iterator, take.getCategory(), false, false);
        }

        return new ProcessorResponse.CoreDTO(
            completedCredit.get(),
            coreJson.getRequiredCredit(),
            Collections.emptySet(),
            completedCredit.get() >= coreJson.getRequiredCredit()
        );
    }

    /*
     * [영역 상관 있을 때]
     * - category 기준으로 판별한다.
     * - 대체과목인 경우 삭제하지 않고 이수학점만 추가한다.
     *  이유: 대체과목은 다른 영역에 영향을 미치기 때문에 추가적으로 계산되어야 한다.
     */

    private ProcessorResponse.CoreDTO createWhenAreaConfirmed(
        LinkedList<Take> allTakeList,
        Map<Category, Set<String>> areaAlternativeCodeMap, // 영역 대체
        Map<String, Set<String>> alternativeCourseMap, // 과목 대체 코드
        Set<Category> requiredAreaSet, // 필요 영역
        Integer requiredCredit
    ) {
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (checkByCategory(take, iterator, requiredAreaSet)) {
                continue;
            }

            /* 영역 대체과목 확인 로직
             * ** 여기서는 삭제를 하지 않는다. **
             */
            checkByAreaAlternative(
                take,
                iterator,
                requiredAreaSet,
                areaAlternativeCodeMap,
                alternativeCourseMap
            );
        }

        Set<Category> uncompletedAreas = getUncompletedAreaSet(requiredAreaSet);
        return new ProcessorResponse.CoreDTO(
            completedCredit.get(),
            requiredCredit,
            uncompletedAreas,
            uncompletedAreas.isEmpty()
        );
    }

    /*
     * category 기준 logic
     */
    private boolean checkByCategory(
        Take take,
        Iterator<Take> iterator,
        Set<Category> requiredAreaSet
    ) {
        if (isCore(take.getCategory(), requiredAreaSet)) {
            updateStatus(take, iterator, take.getCategory(), true, false);
            return true;
        }
        return false;
    }

    /*
     * 영역 대체과목 기준 logic
     */
    private void checkByAreaAlternative(
        Take take,
        Iterator<Take> iterator,
        Set<Category> requiredAreaSet,
        Map<Category, Set<String>> areaAlternativeCodeMap,
        Map<String, Set<String>> alternativeCourseMap
    ) {
        String takenCode = take.getEffectiveCourse().getCode();

        for (Category area : requiredAreaSet) {
            Set<String> areaAlternativeCodeSet = areaAlternativeCodeMap.get(area);
            if (areaAlternativeCodeSet == null) {
                continue;
            }

            if (areaAlternativeCodeSet.contains(takenCode)) {
                updateStatus(take, iterator, area, true, true);
                return;
            }

            Set<String> altCodeSet = alternativeCourseMap.get(takenCode);
            if (altCodeSet == null) {
                return;
            }

            for (String altCode : altCodeSet) {
                if (areaAlternativeCodeSet.contains(altCode)) {
                    updateStatus(take, iterator, area, true, true);
                    return;
                }
            }
        }
    }

    private Set<Category> getUncompletedAreaSet(Set<Category> requiredAreaSet) {
        Set<Category> uncompletedAreaSet = new HashSet<>();

        for (Category area : requiredAreaSet) {
            if (!completedAreas.get().contains(area)) {
                uncompletedAreaSet.add(area);
            }
        }
        return uncompletedAreaSet;
    }

    private boolean isCore(Category category, Set<Category> requiredAreaSet) {
        return requiredAreaSet.contains(category);
    }

    private void updateStatus(
        Take take,
        Iterator<Take> iterator,
        Category area,
        boolean isAreaConfirmed,
        boolean isAlternativeArea
    ) {
        completedCredit.set(
            completedCredit.get() + take.getEffectiveCourse().getCredit()
        );
        if (isAreaConfirmed) {
            completedAreas.get().add(area);
        }
        if (!isAlternativeArea) {
            iterator.remove();
        }
    }

}
