package icurriculum.domain.curriculum.data;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * 필수이수학점
 */
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class RequiredCredit {

    @JsonProperty("총필요학점")
    private Integer totalNeedCredit;

    @JsonProperty("단일전공_필요학점")
    private Integer singleNeedCredit;

    @JsonProperty("복수_연계_융합_전공_필요학점")
    private Integer secondNeedCredit;

    @JsonProperty("부전공_필요학점")
    private Integer minorNeedCredit;

    @Builder
    private RequiredCredit(
        Integer totalNeedCredit,
        Integer singleNeedCredit,
        Integer secondNeedCredit,
        Integer minorNeedCredit
    ) {
        this.totalNeedCredit = totalNeedCredit;
        this.singleNeedCredit = singleNeedCredit;
        this.secondNeedCredit = secondNeedCredit;
        this.minorNeedCredit = minorNeedCredit;

        validate();
    }

    public void validate() {
        if (totalNeedCredit == null || singleNeedCredit == null ||
            secondNeedCredit == null || minorNeedCredit == null
        ) {
            throw new GeneralException(ErrorStatus.CURRICULUM_MISSING_VALUE, this);
        }
    }
}

/*
 * {
 *   "총필요학점": 130,
 *   "단일전공_필요학점": 65,
 *   "복수_연계_융합_전공_필요학점": 39,
 *   "부전공_필요학점": 21
 * }
 */
