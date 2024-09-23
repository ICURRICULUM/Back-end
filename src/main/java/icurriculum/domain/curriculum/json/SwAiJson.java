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
 * [SW_AI]
 * - 인정과목: SW_AI로 인정되는 과목코드들이 저장
 * - 영역대체과목: 해당과목을 들었으면 인정되는 경우이다. 이 상황에서는 해당과목이 다른 영역에 영향을 줄 수 있다
 * - ex. 전기공학과 ("ACE2105", "ACE2103","ACE1307") => 교양필수
 */


@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class SwAiJson {

    @Getter
    @JsonProperty("인정과목")
    private Set<String> approvedCodeSet;

    @Getter
    @JsonProperty("영역대체과목")
    private Set<String> areaAlternativeCodeSet;

    @Getter
    @JsonProperty("필요학점")
    private Integer requiredCredit;

    @JsonProperty("추가정보")
    private Map<String, Object> addtionalFieldMap;

    public Optional<Object> getAdditionalInfo(String key) {
        return Optional.ofNullable(addtionalFieldMap.get(key));
    }

}

/*
 * {
 *   "인정과목": ["CEE1101", ...],
 *   "영역대체과목": ["ACE2105", "ACE2103", "ACE1307"],
 *   "필요학점": 3,
 *   "추가정보": {}
 * }
 */
