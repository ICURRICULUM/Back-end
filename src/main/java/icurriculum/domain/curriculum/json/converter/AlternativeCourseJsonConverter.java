package icurriculum.domain.curriculum.json.converter;

import icurriculum.domain.curriculum.json.AlternativeCoursesJson;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlternativeCourseJsonConverter extends JsonConverter<AlternativeCoursesJson> {

    public AlternativeCourseJsonConverter() {
        super(AlternativeCoursesJson.class);
    }

}
