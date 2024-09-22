package icurriculum.domain.curriculum.json;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.domain.take.Category;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/*
 * 교과과정
 */

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CurriculumCodesJson {

    /*
     * 전공필수, 전공선택, 교양필수만 있어야 함
     */

    @JsonProperty("교과과정")
    private Map<Category, Set<String>> codes;

    public Set<String> findRequiredCodesByCategory(Category category) {
        validateCategory(category);
        return codes.get(category);
    }

    private void validateCategory(Category category) {
        if (category == Category.전공필수 || category == Category.전공선택 || category == Category.교양필수) {
            return;
        }

        throw new GeneralException(ErrorStatus.CURRICULUM_NOT_VALID_CATEGORY, category);
    }
}