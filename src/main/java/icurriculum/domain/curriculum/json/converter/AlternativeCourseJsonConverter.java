package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.AlternativeCourseJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlternativeCourseJsonConverter extends JsonConverter<AlternativeCourseJson> {
    public AlternativeCourseJsonConverter() {
        super(AlternativeCourseJson.class);
    }

}
