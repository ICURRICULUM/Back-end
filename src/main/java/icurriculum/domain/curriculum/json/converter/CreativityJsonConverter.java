package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.CreativityJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CreativityJsonConverter extends JsonConverter<CreativityJson> {

    public CreativityJsonConverter() {
        super(CreativityJson.class);
    }
}
