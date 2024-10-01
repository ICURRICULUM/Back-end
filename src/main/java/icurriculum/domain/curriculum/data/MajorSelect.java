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
 * [전공선택]
 *
 * - 크롤러로 가져오는 데이터, 필요 시 수정해야 함
 * - 추가정보 저장 가능 (additionalInfoMap)
 */

@NoArgsConstructor(access = PROTECTED)
@ToString
public class MajorSelect {

    @Getter
    @JsonProperty("과목코드")
    private Set<String> codeSet = new HashSet<>();

    @JsonProperty("추가정보")
    private Map<String, Object> additionalInfoMap = new HashMap<>();

    @Builder
    private MajorSelect(
        Set<String> codeSet,
        Map<String, Object> additionalInfoMap
    ) {
        this.codeSet = (codeSet != null) ?
            codeSet : new HashSet<>();

        this.additionalInfoMap = (additionalInfoMap != null) ?
            additionalInfoMap : new HashMap<>();

        validate();
    }

    public Optional<Object> getAdditionalInfo(String key) {
        return Optional.ofNullable(additionalInfoMap.get(key));
    }

    public void validate() {
        if (codeSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.CURRICULUM_MISSING_VALUE, this);
        }
    }
}
