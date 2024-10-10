package icurriculum.domain.curriculum.data;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * [창의]
 *
 * - 인정과목: 창의로 인정받을 수 있는 과목코드들을 모두 넣는다. (보통 프런티어대학 기준)
 * - 추가정보 저장 가능 (additionalInfoMap)
 */

@NoArgsConstructor(access = PROTECTED)
@ToString
public class Creativity {

    @Getter
    @JsonProperty("인정과목")
    private Set<String> approvedCodeSet = new HashSet<>();

    @Getter
    @JsonProperty("요구학점")
    private Integer requiredCredit;

    @JsonProperty("추가정보")
    private Map<String, Object> additionalInfoMap = new HashMap<>();

    @Builder
    private Creativity(
        Set<String> approvedCodeSet,
        Integer requiredCredit,
        Map<String, Object> additionalInfoMap
    ) {
        this.approvedCodeSet = (approvedCodeSet != null) ?
            approvedCodeSet : new HashSet<>();

        this.requiredCredit = requiredCredit;

        this.additionalInfoMap = (additionalInfoMap != null) ?
            additionalInfoMap : new HashMap<>();

        validate();
    }

    public Optional<Object> getAdditionalInfo(String key) {
        return Optional.ofNullable(additionalInfoMap.get(key));
    }

    public void validate() {
        if (requiredCredit == null) {
            throw new GeneralException(ErrorStatus.CREATIVITY_INVALID_DATA, this);
        }
        if (requiredCredit.equals(0) && !approvedCodeSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.CREATIVITY_INVALID_DATA, this);
        }
        if (!requiredCredit.equals(0) && approvedCodeSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.CREATIVITY_INVALID_DATA, this);
        }
    }
}

/*
 * {
 *   "인정과목": ["CRE4302", ... ],
 *   "요구학점": 3,
 *   "추가정보": {}
 * }
 */
