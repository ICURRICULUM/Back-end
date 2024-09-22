package icurriculum.domain.graduation.processor.creativity.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface CreativityStrategy {

    ProcessorResponse.CreativityDTO execute(
        ProcessorRequest.CreativityDTO request,
        LinkedList<Take> allTakeList
    );
}
