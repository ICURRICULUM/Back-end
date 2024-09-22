package icurriculum.domain.graduation.processor.majorselect;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.processor.majorselect.strategy.MajorSelectStrategy;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MajorSelectProcessor implements
    Processor<ProcessorRequest.MajorSelectDTO, ProcessorResponse.MajorSelectDTO> {

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
