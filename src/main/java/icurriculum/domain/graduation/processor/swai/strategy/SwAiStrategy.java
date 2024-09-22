package icurriculum.domain.graduation.processor.swai.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface SwAiStrategy {

    ProcessorResponse.SwAiDTO execute(
        ProcessorRequest.SwAiDTO request,
        LinkedList<Take> allTakeList
    );

}
