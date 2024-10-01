package icurriculum.domain.graduation.dto;

import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;

public abstract class GraduationResponse {

    public record AllDTO() {

    }

    public record MainDTO(
        ProcessorResponse.SwAiDTO swAiDTO,
        ProcessorResponse.CreativityDTO creativityDTO,
        ProcessorResponse.CoreDTO coreDTO,
        ProcessorResponse.MajorRequiredDTO majorRequiredDTO,
        ProcessorResponse.MajorSelectDTO majorSelectDTO,
        ProcessorResponse.GeneralRequiredDTO generalRequiredDTO,
        int totalNeedCredit
    ) {

    }

    public record DoubleDTO(
    ) {

    }

    public record ConvergentDTO(
    ) {

    }

    public record InterDTO(
    ) {

    }

    public record MinorDTO(
    ) {

    }

}