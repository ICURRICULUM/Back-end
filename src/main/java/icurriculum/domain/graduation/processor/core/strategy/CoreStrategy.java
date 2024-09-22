package icurriculum.domain.graduation.processor.core.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface CoreStrategy {

    ProcessorResponse.CoreDTO execute(
        ProcessorRequest.CoreDTO request,
        LinkedList<Take> allTakeList
    );
}
