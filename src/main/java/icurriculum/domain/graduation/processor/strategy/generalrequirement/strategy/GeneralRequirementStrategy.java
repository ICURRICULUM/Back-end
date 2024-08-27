package icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy;

import icurriculum.domain.graduation.processor.dto.ProcessorDto.GeneralRequirementDto;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.GeneralRequirement;
import icurriculum.domain.take.Take;

import java.util.List;

public interface GeneralRequirementStrategy {
    GeneralRequirementDto execute(GeneralRequirement requirement, List<Take> takes);

}
