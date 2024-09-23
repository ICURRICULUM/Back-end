package icurriculum.domain.curriculum.json;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.domain.take.Category;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * [교과과정]
 * - 교과과정 코드 저장 및 특이사항 저장
 * ex. 트랙 별 이수
 */

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class CurriculumCodeJson {

    @JsonProperty("교과과정")
    private Map<Category, Set<String>> codeMap;

    @JsonProperty("추가정보")
    private Map<String, Object> addtionalFieldMap;

    public Set<String> getRequiredCodeByCategory(Category category) {
        validateCategory(category);
        return codeMap.get(category);
    }

    public Optional<Object> getAdditionalInfo(String key) {
        return Optional.ofNullable(addtionalFieldMap.get(key));
    }

    /*
     * 전공필수, 전공선택, 교양필수만 있어야 함
     */
    private void validateCategory(Category category) {
        if (category == Category.전공필수 || category == Category.전공선택 || category == Category.교양필수) {
            return;
        }

        throw new GeneralException(ErrorStatus.CURRICULUM_NOT_VALID_CATEGORY, category);
    }
}

/*
 * {
 *   "교과과정": {
 *     "전공필수": ["COE1012", "COE1022"],
 *     "전공선택": ["ELT2012", "ECT2022"],
 *     "교양필수": ["GEN3011", "GEN3021"]
 *   },
 *   "추가정보": {
 *     "특이사항": "트랙별 이수 조건"
 *   }
 * }
 */