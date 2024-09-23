package icurriculum.domain.graduation.processor.generalselect.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest.CreditData;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.CourseUtils;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonGeneralSelectStrategy implements GeneralSelectStrategy {

    @Override
    public ProcessorResponse.GeneralSelectDTO execute(
        ProcessorRequest.GeneralSelectDTO request,
        LinkedList<Take> allTakeList
    ) {
        validateAllRemainingTakeListGeneralSelect(allTakeList);
        int generalSelectCompletedCredit = CourseUtils.calculateTotalCredit(allTakeList);

        return getGeneralSelectDTO(generalSelectCompletedCredit, request.creditData());
    }

    private void validateAllRemainingTakeListGeneralSelect(LinkedList<Take> allTakeList) {
        for (Take take : allTakeList) {
            if (take.getCategory() != Category.교양선택) {
                log.error("남아있는 take 는 모두 교양선택이어야 합니다: {}", take);
                throw new GeneralException(ErrorStatus.CATEGORY_IS_NOT_VALID);
            }
        }
    }

    private ProcessorResponse.GeneralSelectDTO getGeneralSelectDTO(
        final int generalSelectCompletedCredit,
        CreditData creditData
    ) {
        int completedCreditExceptGeneralSelect = creditData.completedCreditExceptGeneralSelect();
        int totalRequiredCredit = creditData.requiredCreditJson().getTotalRequiredCredit();

        return new ProcessorResponse.GeneralSelectDTO(
            generalSelectCompletedCredit,
            generalSelectCompletedCredit + completedCreditExceptGeneralSelect >= totalRequiredCredit
        );
    }

}




