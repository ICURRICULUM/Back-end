package icurriculum.domain.graduation.processor.swai.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class CommonSwAiStrategy implements SwAiStrategy {

    @Override
    public ProcessorResponse.SwAiDTO execute(
        ProcessorRequest.SwAiDTO request,
        LinkedList<Take> allTakeList
    ) {
        int completedCredit = 0;

        Set<String> areaAlternativeCodeSet = request.swAiJson()
            .getAreaAlternativeCodeSet(); // SW_AI 영역 대체과목
        Map<String, Set<String>> alternativeCourseCodeMap = request.alternativeCourseCodeMap(); // 대체 과목

        /*
         * [Logic]
         *
         * - Category SW_AI인 take는 이수학점을 추가하고 삭제한다.
         * - 영역 대체과목이라면 삭제하지 않고 이수학점만 추가한다.
         *  삭제하지 않는 이유: 대체과목은 교양필수에도 포함되기 때문에 교양필수에서 계산되어야 한다.
         */
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (take.getCategory() == Category.SW_AI) {
                completedCredit += take.getEffectiveCourse().getCredit();
                iterator.remove();
                continue;
            }

            String takenCode = take.getEffectiveCourse().getCode();
            if (isTakenByAreaAlternative(takenCode, areaAlternativeCodeSet,
                alternativeCourseCodeMap)) {
                completedCredit += take.getEffectiveCourse().getCredit();
            }
        }

        int requiredCredits = request.swAiJson().getRequiredCredit();
        boolean isClear = completedCredit >= requiredCredits;

        return new ProcessorResponse.SwAiDTO(completedCredit, requiredCredits, isClear);
    }

    private boolean isTakenByAreaAlternative(
        String takenCode,
        Set<String> areaAlternativeCodeSet,
        Map<String, Set<String>> alternativeCourseCodeMap
    ) {
        if (areaAlternativeCodeSet.contains(takenCode)) {
            return true;
        }

        Set<String> altCodeSet = alternativeCourseCodeMap.get(takenCode);
        if (altCodeSet == null) {
            return false;
        }

        for (String altCode : altCodeSet) {
            if (areaAlternativeCodeSet.contains(altCode)) {
                return true;
            }
        }
        return false;
    }
}
