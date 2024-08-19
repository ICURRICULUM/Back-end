package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.CoreJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CoreJsonConverter extends JsonConverter<CoreJson> {
    public CoreJsonConverter() {
        super(CoreJson.class);
    }
}
