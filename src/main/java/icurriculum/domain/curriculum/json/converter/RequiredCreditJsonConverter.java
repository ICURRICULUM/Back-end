package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.RequiredCreditJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RequiredCreditJsonConverter extends JsonConverter<RequiredCreditJson> {

    public RequiredCreditJsonConverter() {
        super(RequiredCreditJson.class);
    }

}