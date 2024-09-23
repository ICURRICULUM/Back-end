package icurriculum.domain.curriculum.json;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * 대체 과목 코드
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class AlternativeCourseJson {

    @JsonProperty("대체과목코드")
    private Map<String, Set<String>> alternativeCourseCodeMap;

}

/*
{
    "ABC1234" : ["ABB1236", "ABB1237"],
    "ABC1235" : ["ABB1231", "ABB1232"]
}
*/
