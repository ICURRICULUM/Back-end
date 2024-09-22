package icurriculum.domain.graduation.processor.majorselect.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface MajorSelectStrategy {

    ProcessorResponse.MajorSelectDTO execute(
        ProcessorRequest.MajorSelectDTO request,
        LinkedList<Take> allTakeList
    );

}
