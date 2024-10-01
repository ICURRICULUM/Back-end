package icurriculum.domain.graduation.dto;

import icurriculum.domain.graduation.dto.GraduationResponse.AllDTO;
import icurriculum.domain.graduation.dto.GraduationResponse.MainDTO;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GraduationConverter {

    public static GraduationResponse.AllDTO toAllDTO(
        List<Object> DTOList,
        final int totalCompletedCredit
    ) {
        for (Object o : DTOList) {
            if (o instanceof GraduationResponse.MainDTO response) {
                boolean isOverTotalNeedCredit = response.totalNeedCredit() <= totalCompletedCredit;
                log.info("totalCompletedCredit: {}", totalCompletedCredit);
                log.info("isOverTotalNeedCredit: {}", isOverTotalNeedCredit);

                continue;
            }
            if (o instanceof GraduationResponse.DoubleDTO) {
                continue;
            }
            if (o instanceof GraduationResponse.ConvergentDTO) {
                continue;
            }
            if (o instanceof GraduationResponse.InterDTO) {
                continue;
            }
            if (o instanceof GraduationResponse.MinorDTO) {

            }
        }
        return new AllDTO();
    }

    public static GraduationResponse.MainDTO toMainDTO(
        ProcessorResponse.SwAiDTO swAiDTO,
        ProcessorResponse.CreativityDTO creativityDTO,
        ProcessorResponse.CoreDTO coreDTO,
        ProcessorResponse.MajorRequiredDTO majorRequiredDTO,
        ProcessorResponse.MajorSelectDTO majorSelectDTO,
        ProcessorResponse.GeneralRequiredDTO generalRequiredDTO,
        final int totalNeedCredit
    ) {
        log.info("swAi: {}", swAiDTO);
        log.info("creativity: {}", creativityDTO);
        log.info("core: {}", coreDTO);
        log.info("majorRequired: {}", majorRequiredDTO);
        log.info("majorSelect: {}", majorSelectDTO);
        log.info("generalRequired: {}", generalRequiredDTO);
        log.info("totalNeedCredit: {}", totalNeedCredit);

        return new MainDTO(
            swAiDTO,
            creativityDTO, coreDTO,
            majorRequiredDTO,
            majorSelectDTO,
            generalRequiredDTO,
            totalNeedCredit
        );
    }
}
