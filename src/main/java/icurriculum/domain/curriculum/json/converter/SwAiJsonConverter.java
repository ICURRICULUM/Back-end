package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.SwAiJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SwAiJsonConverter extends JsonConverter<SwAiJson> {

    public SwAiJsonConverter() {
        super(SwAiJson.class);
    }
}
