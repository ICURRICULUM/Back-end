package icurriculum.global.util;

import icurriculum.domain.graduation.processor.dto.ProcessorResponse.CoreDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.CreativityDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.GeneralRequiredDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.MajorSelectDTO;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse.SwAiDTO;

public abstract class DTOUtils {

    public static int calculateCompletedCreditExceptGeneralSelect(
        CreativityDTO creativityDTO,
        SwAiDTO swAiDTO,
        CoreDTO coreDTO,
        MajorSelectDTO majorSelectDTO,
        GeneralRequiredDTO generalRequiredDTO
    ) {
        int totalCredit = 0;
        totalCredit += creativityDTO.getCompletedCredit();
        totalCredit += swAiDTO.getCompletedCredit();
        totalCredit += coreDTO.getCompletedCredit();
        totalCredit += generalRequiredDTO.getCompletedCredit();
        totalCredit += majorSelectDTO.getTotalMajorCompletedCredit();

        return totalCredit;
    }
}
