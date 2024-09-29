package icurriculum.domain.curriculum.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import icurriculum.domain.take.Category;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CoreTest {

    @Test
    @DisplayName("빌더를 통해 Core 객체를 생성할 때 기본값이 설정되는지 테스트")
    void 빌더로_Core_객체_생성시_기본값_설정_테스트() {
        // given
        Core core = Core.builder()
            .isAreaFixed(true)
            .requiredCredit(9)
            .build();

        // then
        assertThat(core.getIsAreaFixed()).isTrue();
        assertThat(core.getRequiredCredit()).isEqualTo(9);
        assertThat(core.getRequiredAreaSet()).isEmpty();
    }

    @Test
    @DisplayName("영역별 지정과목과 대체과목이 제대로 설정되고 가져올 수 있는지 테스트")
    void 영역별_지정과목과_대체과목_설정_및_가져오기_테스트() {
        // given
        Map<Category, Set<String>> areaDeterminedMap = new HashMap<>();
        Set<String> determinedCodeSet = Set.of("GED2101");
        areaDeterminedMap.put(Category.핵심교양1, determinedCodeSet);

        Map<Category, Set<String>> areaAlternativeMap = new HashMap<>();
        Set<String> alternativeCodeSET = Set.of("ACE1301", "ACE1306");
        areaAlternativeMap.put(Category.핵심교양6, alternativeCodeSET);

        Core core = Core.builder()
            .isAreaFixed(true)
            .requiredCredit(9)
            .areaDeterminedCodeMap(areaDeterminedMap)
            .areaAlternativeCodeMap(areaAlternativeMap)
            .build();

        // then
        assertThat(core.getAreaDeterminedCodeSet(Category.핵심교양1)).containsExactlyInAnyOrder(
            "GED2101");
        assertThat(core.getAreaAlternativeCodeSet(Category.핵심교양6)).containsExactlyInAnyOrder(
            "ACE1301", "ACE1306");
    }

    @Test
    @DisplayName("존재하지 않는 DeterminedCodeSet에 대해 빈 값을 반환하는지 테스트")
    void 존재하지_않는_DeterminedCodeSet_빈_값_반환_테스트() {
        // given
        Core core = Core.builder()
            .isAreaFixed(true)
            .requiredCredit(9)
            .build();

        // when
        Set<String> result = core.getAreaDeterminedCodeSet(Category.핵심교양3);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("필수 값 누락 시 예외 발생 테스트")
    void 필수값_누락시_예외_발생_테스트() {
        // when & then
        assertThatThrownBy(() -> Core.builder().build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }

    @Test
    @DisplayName("유효하지 않은 Category 사용 시 예외 발생 테스트")
    void 유효하지_않은_카테고리_사용시_예외_발생_테스트() {
        // given
        Core core = Core.builder()
            .isAreaFixed(true)
            .requiredCredit(9)
            .build();

        // then
        assertThatThrownBy(() -> core.getAreaDeterminedCodeSet(Category.교양선택))
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CORE_NOT_VALID_CATEGORY);
    }
}
