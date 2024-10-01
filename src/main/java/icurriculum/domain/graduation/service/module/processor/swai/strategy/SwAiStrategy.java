package icurriculum.domain.graduation.service.module.processor.swai.strategy;

import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface SwAiStrategy {

    ProcessorResponse.SwAiDTO execute(
        ProcessorRequest.SwAiDTO request,
        LinkedList<Take> allTakeList
    );

}
