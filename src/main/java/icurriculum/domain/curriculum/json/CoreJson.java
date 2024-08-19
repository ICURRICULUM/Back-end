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

}

/**
 * {
 *   "영역_지정여부": true,
 *   "요구학점": 9,
 *   "필수영역": ["핵심교양1", "핵심교양2", "핵심교양4"],
 *   "영역별_지정과목": {
 *     "핵심교양1": ["GED2101"]
 *   }
 * }
 */