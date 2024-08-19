package icurriculum.domain.curriculum.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

/**
 * 필수_이수학점
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RequirementCreditJson {

    @JsonProperty("총이수")
    private Integer totalRequiredCredit;

    @JsonProperty("단일전공")
    private Integer singleRequiredCredit;

    @JsonProperty("복수_연계_융합_전공")
    private Integer secondRequiredCredit;

    @JsonProperty("부전공")
    private Integer minorRequiredCredit;

}

/**
 * {
 *   "총이수": 130,
 *   "단일전공": 65,
 *   "복수_연계_융합_전공": 39,
 *   "부전공": 21
 * }
 */