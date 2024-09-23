package icurriculum.domain.graduation.processor.creativity.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class CommonCreativityStrategy implements CreativityStrategy {

    @Override
    public ProcessorResponse.CreativityDTO execute(
        ProcessorRequest.CreativityDTO request,
        LinkedList<Take> allTakeList
    ) {
        int completedCredit = 0;

        /*
         * [Logic]
         *
         * - Category 창의 과목은 삭제하고, 이수학점을 추가한다.
         * - 대체과목을 통해 인정되면 삭제하고, 이수학점을 추가한다. **여기서는 영역 대체가 아니다!, 학수번호가 바뀐 상황**
         */
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (take.getCategory() == Category.창의) {
                completedCredit += take.getEffectiveCourse().getCredit();
                iterator.remove();
                continue;
            }

            if (isTakenByCodeAlternative(
                take.getEffectiveCourse().getCode(),
                request.creativityJson().getApprovedCodeSet(),
                request.alternativeCourseCodeMap())
            ) {
                completedCredit += take.getEffectiveCourse().getCredit();
                iterator.remove();
            }
        }

        int requiredCredits = request.creativityJson().getRequiredCredit();
        boolean isClear = completedCredit >= requiredCredits;

        return new ProcessorResponse.CreativityDTO(completedCredit, requiredCredits, isClear);
    }

    private boolean isTakenByCodeAlternative(
        String takenCode,
        Set<String> approvedCodeSet,
        Map<String, Set<String>> alternativeCourseCodeMap
    ) {
        Set<String> altCodeSet = alternativeCourseCodeMap.get(takenCode);
        if (altCodeSet == null) {
            return false;
        }
        for (String altCode : altCodeSet) {
            if (approvedCodeSet.contains(altCode)) {
                return true;
            }
        }
        return false;
    }

}
