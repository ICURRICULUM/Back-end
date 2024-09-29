package icurriculum.domain.graduation.processor.generalselect.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest.CreditData;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.GeneralSelectDTO;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonGeneralSelectStrategy implements GeneralSelectStrategy {

    @Override
    public ProcessorResponse.GeneralSelectDTO execute(
        ProcessorRequest.GeneralSelectDTO request,
        LinkedList<Take> allTakeList
    ) {
        ProcessorResponse.GeneralSelectDTO response = new GeneralSelectDTO();
        validateRemainTakeListGeneralSelect(allTakeList);

        handleResponse(
            allTakeList,
            request.creditData(),
            response
        );

        return response;
    }

    private void handleResponse(
        LinkedList<Take> allTakeList,
        CreditData creditData,
        ProcessorResponse.GeneralSelectDTO response
    ) {
        response.update(allTakeList, creditData);
        response.checkIsClear();
    }

    private void validateRemainTakeListGeneralSelect(LinkedList<Take> allTakeList) {
        for (Take take : allTakeList) {
            if (take.getCategory() != Category.교양선택) {
                log.error("남아있는 take 는 모두 교양선택이어야 합니다: {}", take);
                throw new GeneralException(ErrorStatus.CATEGORY_IS_NOT_VALID);
            }
        }
    }

}




