package icurriculum.domain.graduation.service.module.processor.majorselect.strategy;

import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorConverter;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CreditWithData;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.service.module.processor.majorselect.MajorSelectResult;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.util.GraduationUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonMajorSelectStrategy implements MajorSelectStrategy {

    /*
     * [Logic]
     *
     * 1. 전공선택 과목은 삭제하고, 이수학점을 추가한다.
     * 2. 대체과목을 통해 인정되면 삭제하고, 이수학점을 추가한다.
     *      여기서는 영역 대체가 아니다!, 학수번호가 바뀐 상황
     */
    @Override
    public ProcessorResponse.MajorSelectDTO execute(
        ProcessorRequest.MajorSelectDTO request,
        LinkedList<Take> allTakeList
    ) {
        MajorSelectResult result = new MajorSelectResult();
        result.initMajorSelectResult(request.creditWithData());

        handleResult(
            allTakeList,
            request.creditWithData(),
            request.alternativeCourse(),
            result
        );

        return ProcessorConverter.to(result);
    }

    private void handleResult(
        LinkedList<Take> allTakeList,
        CreditWithData creditWithData,
        AlternativeCourse alternativeCourse,
        MajorSelectResult result
    ) {
        Set<String> majorSelectCodeSet = creditWithData.majorSelect().getCodeSet();

        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (isCategoryGeneralRequired(take, majorSelectCodeSet)) {
                result.update(take, iterator);
                continue;
            }

            if (GraduationUtils.isCodeAlternative(
                take,
                majorSelectCodeSet,
                alternativeCourse)
            ) {
                result.update(take, iterator);
            }
        }

        result.checkIsClear();
    }

    /*
     * IPP(현장실습), 학부연구생, 교환학생과 같은 경우
     * Curriculum에 없는 수업이라도 학점으로 인정받을 수 있음
     */
    private boolean isCategoryGeneralRequired(Take take, Set<String> majorSelectCodeSet) {
        return GraduationUtils.isApprovedCategory(take, Category.전공선택) ||
            GraduationUtils.isApproved(take, majorSelectCodeSet);
    }

}




