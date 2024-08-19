package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.CurriculumCodesJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CurriculumCodesJsonConverter extends JsonConverter<CurriculumCodesJson> {
    public CurriculumCodesJsonConverter() {
        super(CurriculumCodesJson.class);
    }

}