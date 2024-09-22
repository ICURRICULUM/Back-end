package icurriculum.domain.graduation.processor.swai;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.processor.swai.strategy.SwAiStrategy;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SwAiProcessor implements
    Processor<ProcessorRequest.SwAiDTO, ProcessorResponse.SwAiDTO> {

    private final Map<DepartmentName, SwAiStrategy> swAiStrategyMap;

    @Override
    public ProcessorResponse.SwAiDTO execute(
        ProcessorRequest.SwAiDTO request,
        LinkedList<Take> allTakeList
    ) {
        return swAiStrategyMap.get(request.departmentName())
            .execute(request, allTakeList);
    }
}
