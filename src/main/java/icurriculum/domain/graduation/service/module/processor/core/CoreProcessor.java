package icurriculum.domain.graduation.service.module.processor.core;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.service.module.processor.Processor;
import icurriculum.domain.graduation.service.module.processor.core.strategy.CoreStrategy;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CoreDTO;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreProcessor implements
    Processor<CoreDTO, ProcessorResponse.CoreDTO> {

    private final Map<DepartmentName, CoreStrategy> coreStrategyMap;

    @Override
    public ProcessorResponse.CoreDTO execute(
        ProcessorRequest.CoreDTO request,
        LinkedList<Take> allTakeList
    ) {
        return coreStrategyMap.get(request.departmentName())
            .execute(request, allTakeList);
    }
}
