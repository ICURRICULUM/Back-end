package icurriculum.domain.curriculum.json;

import icurriculum.domain.take.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static icurriculum.domain.take.Category.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CurriculumCodesJsonTest {

    private CurriculumCodesJson curriculumCodesJson;

    @BeforeEach
    void setUp() {
        Map<Category, Set<String>> codes = new HashMap<>();

        codes.put(전공필수,
                Set.of("CSE1101", "CSE1102", "CSE1103", "CSE2101", "CSE2112", "CSE4205")
        );

        codes.put(전공선택,
                Set.of(
                        "ICE4029", "IEN3204", "CSE1105", "CSE2103", "CSE2104", "CSE2105", "CSE2107",
                        "CSE3101", "CSE3201", "CSE3203", "CSE3206", "CSE3308", "CSE3309", "CSE4201",
                        "CSE4202", "CSE4204", "CSE4308", "CSE3102", "CSE3207", "CSE4302", "CSE4305",
                        "CSE4314", "CSE3202", "CSE3205", "CSE3302", "CSE3303", "CSE3304", "CSE3307",
                        "CSE4312", "CSE3204", "CSE4301", "CSE4303", "CSE4304", "CSE4307"
                ));

        codes.put(교양필수,
                Set.of(
                        "GEB1112", "GEB1114", "GEB1115", "GEB1124", "GEB1131", "GEB1107",
                        "GEB1201", "ACE1204", "ACE1312", "ACE2101",
                        "ACE2104", "ACE2106", "MTH1001", "MTH1002", "PHY1001", "PHY1002", "PHY1003", "PHY1004"
                ));
        curriculumCodesJson = new CurriculumCodesJson(codes);
    }

    @Test
    @DisplayName("전공필수, 전공선택, 교양필수 카테고리에 대한 코드를 정상적으로 반환")
    void findCodesByCategory_ShouldReturnCodesFor전공필수() {
        // when
        Set<String> 전공필수codes = curriculumCodesJson.findRequiredCodesByCategory(전공필수);
        Set<String> 전공선택codes = curriculumCodesJson.findRequiredCodesByCategory(전공선택);
        Set<String> 교양필수codes = curriculumCodesJson.findRequiredCodesByCategory(교양필수);

        // then
        assertThat(전공필수codes).containsExactlyInAnyOrder(
                "CSE1101", "CSE1102", "CSE1103", "CSE2101", "CSE2112", "CSE4205");
        assertThat(전공선택codes).containsExactlyInAnyOrder(
                "ICE4029", "IEN3204", "CSE1105", "CSE2103", "CSE2104", "CSE2105", "CSE2107",
                "CSE3101", "CSE3201", "CSE3203", "CSE3206", "CSE3308", "CSE3309", "CSE4201",
                "CSE4202", "CSE4204", "CSE4308", "CSE3102", "CSE3207", "CSE4302", "CSE4305",
                "CSE4314", "CSE3202", "CSE3205", "CSE3302", "CSE3303", "CSE3304", "CSE3307",
                "CSE4312", "CSE3204", "CSE4301", "CSE4303", "CSE4304", "CSE4307");
        assertThat(교양필수codes).containsExactlyInAnyOrder(
                "GEB1112", "GEB1114", "GEB1115", "GEB1124", "GEB1131", "GEB1107",
                "GEB1201", "ACE1204", "ACE1312", "ACE2101",
                "ACE2104", "ACE2106", "MTH1001", "MTH1002", "PHY1001", "PHY1002", "PHY1003", "PHY1004");
    }


    /**
     * Todo 예외 추후 정의
     */
    @Test
    @DisplayName("잘못된 카테고리로 코드 조회 시 예외 발생")
    void findCodesByCategory_ShouldThrowExceptionForInvalidCategory() {
        // when & then
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(핵심교양1))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(핵심교양2))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(핵심교양3))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(핵심교양4))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(핵심교양5))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(핵심교양6))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(SW_AI))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(창의))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> curriculumCodesJson.findRequiredCodesByCategory(교양선택))
                .isInstanceOf(RuntimeException.class);
    }
}
