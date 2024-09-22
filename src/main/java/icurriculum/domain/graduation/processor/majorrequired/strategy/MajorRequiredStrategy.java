package icurriculum.domain.graduation.processor.majorrequired.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface MajorRequiredStrategy {

    ProcessorResponse.MajorRequiredDTO execute(
        ProcessorRequest.MajorRequiredDTO request,
        LinkedList<Take> allTakeList
    );

}
