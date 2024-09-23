package icurriculum.domain.curriculum.json;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * 필수_이수학점
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class RequiredCreditJson {

    @JsonProperty("총필요학점")
    private Integer totalRequiredCredit;

    @JsonProperty("단일전공_필요학점")
    private Integer singleRequiredCredit;

    @JsonProperty("복수_연계_융합_전공_필요학점")
    private Integer secondRequiredCredit;

    @JsonProperty("부전공_필요학점")
    private Integer minorRequiredCredit;

}

/*
 * {
 *   "총필요학점": 130,
 *   "단일전공_필요학점": 65,
 *   "복수_연계_융합_전공_필요학점": 39,
 *   "부전공_필요학점": 21
 * }
 */
