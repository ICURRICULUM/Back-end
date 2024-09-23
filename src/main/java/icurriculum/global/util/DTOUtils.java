package icurriculum.global.util;

import icurriculum.domain.graduation.processor.dto.ProcessorResponse.CoreDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.CreativityDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.GeneralRequiredDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.MajorRequiredDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.MajorSelectDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.SwAiDTO;

public abstract class DTOUtils {

    public static int calculateCompletedCreditExceptGeneralSelect(
        CreativityDTO creativityDTO,
        SwAiDTO swAiDTO,
        CoreDTO coreDTO,
        GeneralRequiredDTO generalRequiredDTO,
        MajorRequiredDTO majorRequiredDTO,
        MajorSelectDTO majorSelectDTO
    ) {
        int totalCredit = 0;
        totalCredit += creativityDTO.completedCredit();
        totalCredit += swAiDTO.completedCredit();
        totalCredit += coreDTO.completedCredit();
        totalCredit += generalRequiredDTO.completedCredit();
        totalCredit += majorRequiredDTO.completedCredit();
        totalCredit += majorSelectDTO.completedCredit();

        return totalCredit;
    }
}
