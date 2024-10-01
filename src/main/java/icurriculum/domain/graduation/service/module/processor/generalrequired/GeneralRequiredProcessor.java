package icurriculum.domain.graduation.service.module.processor.generalrequired;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.service.module.processor.Processor;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.GeneralRequiredDTO;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.service.module.processor.generalrequired.strategy.GeneralRequiredStrategy;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/*
 * 교양필수
 */

@RequiredArgsConstructor
public class GeneralRequiredProcessor implements
    Processor<GeneralRequiredDTO, ProcessorResponse.GeneralRequiredDTO> {

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
