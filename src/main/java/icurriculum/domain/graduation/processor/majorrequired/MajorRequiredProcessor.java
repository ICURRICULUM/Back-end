package icurriculum.domain.graduation.processor.majorrequired;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.processor.majorrequired.strategy.MajorRequiredStrategy;
import icurriculum.domain.take.Take;
import java.util.LinkedList;
import java.util.Map;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MajorRequiredProcessor implements
    Processor<ProcessorRequest.MajorRequiredDTO, ProcessorResponse.MajorRequiredDTO> {

    private final Map<DepartmentName, MajorRequiredStrategy> majorRequiredStrategyMap;

    @Override
    public ProcessorResponse.MajorRequiredDTO execute(
        ProcessorRequest.MajorRequiredDTO request,
        LinkedList<Take> allTakeList
    ) {
        return majorRequiredStrategyMap.get(request.departmentName())
            .execute(request, allTakeList);
    }
}
