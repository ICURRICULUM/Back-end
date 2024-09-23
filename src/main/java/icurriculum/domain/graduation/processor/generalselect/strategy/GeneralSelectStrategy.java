package icurriculum.domain.graduation.processor.generalselect.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface GeneralSelectStrategy {

    ProcessorResponse.GeneralSelectDTO execute(
        ProcessorRequest.GeneralSelectDTO request,
        LinkedList<Take> allTakeList
    );

}
