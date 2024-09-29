package icurriculum.domain.curriculum.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SwAiTest {

    @Test
    @DisplayName("빌더를 통해 SwAi 객체를 생성할 때 성공 테스트")
    void 빌더로_SwAi_객체_생성시_성공_테스트() {
        // given
        SwAi swAi = SwAi.builder()
            .approvedCodeSet(Set.of("CEE1101", "CEE1102"))
            .requiredCredit(3)
            .build();

        // then
        assertThat(swAi.getApprovedCodeSet()).containsExactlyInAnyOrder("CEE1101", "CEE1102");
        assertThat(swAi.getAreaAlternativeCodeSet()).isEmpty();
        assertThat(swAi.getRequiredCredit()).isEqualTo(3);
        assertThat(swAi.getAdditionalInfo("key")).isEmpty();
    }

    @Test
    @DisplayName("인정과목과 영역대체 존재할 때 유효성 검사가 통과되는지 테스트")
    void 인정과목과_영역대체_존재시_유효성_검사_통과_테스트() {
        // given
        Set<String> approvedCodeSet = Set.of("CEE1101", "CEE1102");
        Set<String> areaAlternativeCodeSet = Set.of("ACE2105", "ACE2103");

        SwAi swAi = SwAi.builder()
            .approvedCodeSet(approvedCodeSet)
            .areaAlternativeCodeSet(areaAlternativeCodeSet)
            .requiredCredit(3)
            .build();

        // when & then
        assertThat(swAi.getApprovedCodeSet())
            .containsExactlyInAnyOrder("CEE1101", "CEE1102");
        assertThat(swAi.getAreaAlternativeCodeSet())
            .containsExactlyInAnyOrder("ACE2105", "ACE2103");
        assertThat(swAi.getRequiredCredit())
            .isEqualTo(3);
    }

    @Test
    @DisplayName("추가 정보를 올바르게 가져오는지 테스트")
    void 추가정보_가져오기_테스트() {
        // given
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("key", "value");

        SwAi swAi = SwAi.builder()
            .requiredCredit(3)
            .approvedCodeSet(Set.of("CEE1101", "CEE1102"))
            .additionalInfoMap(additionalInfo)
            .build();

        // then
        assertThat(swAi.getApprovedCodeSet()).containsExactlyInAnyOrder("CEE1101", "CEE1102");
        assertThat(swAi.getAdditionalInfo("key")).contains("value");
        assertThat(swAi.getAdditionalInfo("DUMMY")).isEmpty();
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트")
    void 유효성_검사_실패시_예외_발생_테스트() {
        // when & then
        assertThatThrownBy(() -> SwAi.builder()
            .requiredCredit(3)
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);

        assertThatThrownBy(() -> SwAi.builder()
            .approvedCodeSet(Set.of("CEE1101"))
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }
}
