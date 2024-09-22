package icurriculum.domain.graduation.processor.majorselect.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonMajorSelectStrategy implements MajorSelectStrategy {

    @Override
    public ProcessorResponse.MajorSelectDTO execute(
        ProcessorRequest.MajorSelectDTO request,
        LinkedList<Take> allTakeList
    ) {
        int completedCredit = 0;

        /*
         * [Logic]
         *
         * - Category 전공선택 과목은 삭제하고, 이수학점을 추가한다.
         * - 대체과목을 통해 인정되면 삭제하고, 이수학점을 추가한다. **여기서는 영역 대체가 아니다!, 학수번호가 바뀐 상황**
         */

        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (take.getCategory() == Category.전공선택) {
                completedCredit += take.getEffectiveCourse().getCredit();
                iterator.remove();
                continue;
            }

            if (isTakenByCodeAlternative(
                take.getEffectiveCourse().getCode(),
                request.majorSelectCodeSet(),
                request.alternativeCourseMap())
            ) {
                if (take.getCategory() != Category.전공선택) {
                    log.error("전공선택으로 인정되지만, 카테코리가 전공선택이 아닙니다:{}", take);
                    throw new GeneralException(ErrorStatus.CATEGORY_IS_NOT_VALID, take);
                }

                completedCredit += take.getEffectiveCourse().getCredit();
                iterator.remove();
            }
        }

        return new ProcessorResponse.MajorSelectDTO(completedCredit);
    }

    private boolean isTakenByCodeAlternative(
        String takenCode,
        Set<String> majorSelectCodeSet,
        Map<String, Set<String>> alternativeCourseMap
    ) {
        Set<String> altCodeSet = alternativeCourseMap.get(takenCode);
        if (altCodeSet == null) {
            return false;
        }

        for (String altCode : altCodeSet) {
            if (majorSelectCodeSet.contains(altCode)) {
                return true;
            }
        }
        return false;
    }

}




