package icurriculum.domain.graduation.service.module.processor.generalrequired.strategy;

import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface GeneralRequiredStrategy {

    ProcessorResponse.GeneralRequiredDTO execute(
        ProcessorRequest.GeneralRequiredDTO request,
        LinkedList<Take> allTakeList
    );

}
