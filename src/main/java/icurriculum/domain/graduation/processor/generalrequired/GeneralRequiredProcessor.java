package icurriculum.domain.graduation.processor.generalrequired;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.processor.generalrequired.strategy.GeneralRequiredStrategy;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/*
 * 교양필수
 */

@RequiredArgsConstructor
public class GeneralRequiredProcessor implements
    Processor<ProcessorRequest.GeneralRequiredDTO, ProcessorResponse.GeneralRequiredDTO> {

    private final Map<DepartmentName, GeneralRequiredStrategy> generalRequiredStrategyMap;

    @Override
    public ProcessorResponse.GeneralRequiredDTO execute(
        ProcessorRequest.GeneralRequiredDTO request,
        LinkedList<Take> allTakeList
    ) {
        return generalRequiredStrategyMap.get(request.departmentName())
            .execute(request, allTakeList);
    }
}
