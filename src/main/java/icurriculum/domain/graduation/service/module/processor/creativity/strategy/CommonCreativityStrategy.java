package icurriculum.domain.graduation.service.module.processor.creativity.strategy;

import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.util.GraduationUtils;
import java.util.Iterator;
import java.util.LinkedList;

public class CommonCreativityStrategy implements CreativityStrategy {

    /*
     * [Logic]
     *
     * 1. Category가 창의인 과목은 LinkedList에서 삭제하고, 이수학점을 추가한다.
     * 2. 대체과목을 통해 인정되면 take category에 상관없이 LinkedList에서 삭제하고, 이수학점을 추가한다.
     *      ** 여기서는 영역 대체가 아니다! 학수번호로 대체된 것 **
     */
    @Override
    public ProcessorResponse.CreativityDTO execute(
        ProcessorRequest.CreativityDTO request,
        LinkedList<Take> allTakeList
    ) {
        ProcessorResponse.CreativityDTO response = new ProcessorResponse.CreativityDTO();

        handleResponse(
            allTakeList,
            request.creativity(),
            request.alternativeCourse(),
            response
        );

        return response;
    }

    private void handleResponse(
        LinkedList<Take> allTakeList,
        Creativity creativity,
        AlternativeCourse alternativeCourse,
        ProcessorResponse.CreativityDTO response
    ) {
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (GraduationUtils.isApprovedCategory(take, Category.창의)) {
                response.update(take, iterator);
                continue;
            }

            if (GraduationUtils.isCodeAlternative(
                take,
                creativity.getApprovedCodeSet(),
                alternativeCourse
            )) {
                response.update(take, iterator);
            }
        }

        response.setRequiredCredit(creativity);
        response.checkIsClear();
    }

}
