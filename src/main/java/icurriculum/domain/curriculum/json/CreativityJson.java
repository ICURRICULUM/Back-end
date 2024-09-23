package icurriculum.domain.curriculum.json;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * 창의
 */

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class CreativityJson {

    @Getter
    @JsonProperty("인정과목")
    private Set<String> approvedCodeSet;

    @Getter
    @JsonProperty("요구학점")
    private Integer requiredCredit;

    @JsonProperty("추가정보")
    private Map<String, Object> addtionalFieldMap;

    public Optional<Object> getAdditionalInfo(String key) {
        return Optional.ofNullable(addtionalFieldMap.get(key));
    }

}

/*
 * {
 *   "인정과목": ["CRE4302", ... ],
 *   "요구학점": 3,
 *   "추가정보": {}
 * }
 */
