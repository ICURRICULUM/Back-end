package icurriculum.domain.graduation.service.module.processor.core.strategy;

import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface CoreStrategy {

    ProcessorResponse.CoreDTO execute(
        ProcessorRequest.CoreDTO request,
        LinkedList<Take> allTakeList
    );
}
