package icurriculum.domain.graduation.service.module.processor.dto;

import icurriculum.domain.graduation.service.module.processor.creativity.CreativityResult;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse.*;
import icurriculum.domain.graduation.service.module.processor.core.CoreResult;
import icurriculum.domain.graduation.service.module.processor.generalrequired.GeneralRequiredResult;
import icurriculum.domain.graduation.service.module.processor.majorrequired.MajorRequiredResult;
import icurriculum.domain.graduation.service.module.processor.majorselect.MajorSelectResult;
import icurriculum.domain.graduation.service.module.processor.swai.SwAiResult;

public abstract class ProcessorConverter {

    public static ProcessorResponse.SwAiDTO to(
        SwAiResult result
    ) {
        return new SwAiDTO(
            result.getCompletedCredit(),
            result.getRequiredCredit(),
            result.isClear()
        );
    }

    public static ProcessorResponse.CoreDTO to(
        CoreResult result
    ) {
        return new CoreDTO(
            result.getCompletedCredit(),
            result.getRequiredCredit(),
            result.getUncompletedArea(),
            result.isClear()
        );
    }

    public static ProcessorResponse.CreativityDTO to(
        CreativityResult result
    ) {
        return new CreativityDTO(
            result.getCompletedCredit(),
            result.getRequiredCredit(),
            result.isClear()
        );
    }

    public static ProcessorResponse.GeneralRequiredDTO to(
        GeneralRequiredResult result
    ) {
        return new GeneralRequiredDTO(
            result.getCompletedCredit(),
            result.getRequiredCredit(),
            result.getUncompletedCourseSet(),
            result.isClear()
        );
    }

    public static ProcessorResponse.MajorRequiredDTO to(
        MajorRequiredResult result
    ) {
        return new MajorRequiredDTO(
            result.getCompletedCredit(),
            result.getRequiredCredit(),
            result.getUncompletedCourseList(),
            result.isClear()
        );
    }

    public static ProcessorResponse.MajorSelectDTO to(
        MajorSelectResult result
    ) {
        return new MajorSelectDTO(
            result.getTotalMajorCompletedCredit(),
            result.getTotalMajorRequiredCredit(),
            result.isClear()
        );
    }

}
