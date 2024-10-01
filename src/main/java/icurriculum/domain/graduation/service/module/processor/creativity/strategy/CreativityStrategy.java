package icurriculum.domain.graduation.service.module.processor.creativity.strategy;

import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface CreativityStrategy {

    ProcessorResponse.CreativityDTO execute(
        ProcessorRequest.CreativityDTO request,
        LinkedList<Take> allTakeList
    );
}
