package icurriculum.domain.graduation.service.module.processor.majorselect;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.service.module.processor.Processor;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.MajorSelectDTO;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.service.module.processor.majorselect.strategy.MajorSelectStrategy;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MajorSelectProcessor implements
    Processor<MajorSelectDTO, ProcessorResponse.MajorSelectDTO> {

    private final Map<DepartmentName, MajorSelectStrategy> majorSelectStrategyMap;

    @Override
    public ProcessorResponse.MajorSelectDTO execute(
        ProcessorRequest.MajorSelectDTO request,
        LinkedList<Take> allTakeList
    ) {
        return majorSelectStrategyMap.get(request.departmentName())
            .execute(request, allTakeList);
    }
}
