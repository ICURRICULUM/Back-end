package icurriculum.domain.curriculum.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.domain.take.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

/**
 * 핵심교양
 * 영역별_지정과목 - 해당과목만 꼭 들어야하는 경우
 *  ex. 핵심교양1 - 공윤토
 *
 * 영역별_대체과목 - 해당과목을 들었으면 인정되는 경우이다. 이 상황에서는 해당과목이 다른 영역에 영향을 줄 수 있다
 *  ex. 핵심교양6 - 파이썬과머신러닝, 수치해석 (기계공학과-전공선택)
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CoreJson {

    @JsonProperty("영역_지정여부")
    private Boolean isAreaConfirmed;

    @JsonProperty("요구학점")
    private Integer requiredCredit;

    @JsonProperty("필수영역")
    private Set<Category> requiredAreas;

    @JsonProperty("영역별_지정과목")
    private Map<Category, Set<String>> confirmedCodesByArea;

    @JsonProperty("영역별_대체과목")
    private Map<Category, Set<String>> alternativeCodesByArea;

}

/**
 * {
 * "영역_지정여부": true,
 * "요구학점": 9,
 * "필수영역": ["핵심교양1", "핵심교양2", "핵심교양4"],
 * "영역별_지정과목": {
 * "핵심교양1": ["GED2101"]
 * }
 * "영역별_대체과목": {
 * "핵심교양6": ["ACE1301", "ACE1306"]
 * }
 * }
 */