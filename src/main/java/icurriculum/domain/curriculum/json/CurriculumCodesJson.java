package icurriculum.domain.curriculum.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.domain.take.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

import static icurriculum.domain.take.Category.*;
import static lombok.AccessLevel.PROTECTED;

/**
 * 교과과정
 */

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CurriculumCodesJson {

    /**
     * 전공필수, 전공선택, 교양필수만 있어야 함
     */

    @JsonProperty("교과과정")
    private Map<Category, Set<String>> codes;

    public Set<String> findCodesByCategory(Category category) {
        validateCategory(category);
        return codes.get(category);
    }

    private void validateCategory(Category category) {
        if (category == 전공필수 || category == 전공선택 || category == 교양필수)
            return;
        /**
         * Todo 예외 추후 정의
         */
        throw new RuntimeException("CurriculumCodesJson 에는 전공필수, 전공선택, 교양필수만 존재합니다.");
    }
}

/**
 * {
 * "교과과정": {
 * "전공필수": ["MAJ1101", "MAJ1102"],
 * "전공선택": ["ELC2201"],
 * "교양필수": ["GEN3301"]
 * }
 * }
 */