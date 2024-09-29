package icurriculum.domain.curriculum.data;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * [대체 과목 코드]
 *
 * - 하나의 과목을 대체하는 과목이 여러 개일 수 있어 value 를 Set<String>으로 저장한다.
 */

@NoArgsConstructor(access = PROTECTED)
@ToString
public class AlternativeCourse {

    @JsonProperty("대체과목코드")
    private Map<String, Set<String>> alternativeCourseCodeMap = new HashMap<>();

    @Builder
    private AlternativeCourse(Map<String, Set<String>> alternativeCourseCodeMap) {
        this.alternativeCourseCodeMap = (alternativeCourseCodeMap != null) ?
            alternativeCourseCodeMap : new HashMap<>();
    }

    public Set<String> getAlternativeCodeSet(String code) {
        return alternativeCourseCodeMap.getOrDefault(code, Collections.emptySet());
    }

}

/*
{
    "ABC1234" : ["ABB1236", "ABB1237"],
    "ABC1235" : ["ABB1231", "ABB1232"]
}
*/
