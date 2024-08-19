package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.RequirementCreditJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RequirementCreditJsonConverter extends JsonConverter<RequirementCreditJson> {
    public RequirementCreditJsonConverter() {
        super(RequirementCreditJson.class);
    }

}