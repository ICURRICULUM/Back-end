package icurriculum.domain.graduation.processor.creativity;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.creativity.strategy.CreativityStrategy;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreativityProcessor implements
    Processor<ProcessorRequest.CreativityDTO, ProcessorResponse.CreativityDTO> {

    private final Map<DepartmentName, CreativityStrategy> creativityStrategyMap;

    @Override
    public ProcessorResponse.CreativityDTO execute(
        ProcessorRequest.CreativityDTO request,
        LinkedList<Take> allTakeList
    ) {
        return creativityStrategyMap.get(request.departmentName())
            .execute(request, allTakeList);
    }

}
