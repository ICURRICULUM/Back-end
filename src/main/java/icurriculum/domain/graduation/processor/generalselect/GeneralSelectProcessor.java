package icurriculum.domain.graduation.processor.generalselect;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.processor.generalselect.strategy.GeneralSelectStrategy;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/*
 * 교양필수
 */

@RequiredArgsConstructor
public class GeneralSelectProcessor implements
    Processor<ProcessorRequest.GeneralSelectDTO, ProcessorResponse.GeneralSelectDTO> {

    private final Map<DepartmentName, GeneralSelectStrategy> generalSelectStrategyMap;

    @Override
    public ProcessorResponse.GeneralSelectDTO execute(
        ProcessorRequest.GeneralSelectDTO request,
        LinkedList<Take> allTakeList
    ) {
        return generalSelectStrategyMap.get(request.departmentName())
            .execute(request, allTakeList);
    }
}
