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

public class CreativityTest {

    @Test
    @DisplayName("빌더를 통해 Creativity 객체를 생성할 때 기본값이 설정되는지 테스트")
    void 빌더로_Creativity_객체_생성시_기본값_설정_테스트() {
        // given
        Set<String> approvedCodeSet = Set.of("CRE4302", "CRE4303");

        // when
        Creativity creativity = Creativity.builder()
            .approvedCodeSet(approvedCodeSet)
            .requiredCredit(3)
            .build();

        // then
        assertThat(creativity.getApprovedCodeSet()).containsExactlyInAnyOrder("CRE4302", "CRE4303");
        assertThat(creativity.getRequiredCredit()).isEqualTo(3);
    }

    @Test
    @DisplayName("추가 정보를 올바르게 가져오는지 테스트")
    void 추가정보_가져오기_테스트() {
        // given
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("a", "b");
        additionalInfo.put("c", 1);

        // when
        Creativity creativity = Creativity.builder()
            .requiredCredit(3)
            .approvedCodeSet(Set.of("CRE4302", "CRE4303"))
            .additionalInfoMap(additionalInfo)
            .build();

        // then
        assertThat(creativity.getAdditionalInfo("a")).contains("b");
        assertThat(creativity.getAdditionalInfo("c")).contains(1);
        assertThat(creativity.getAdditionalInfo("nonExistingKey")).isEmpty();
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트")
    void 유효성_검사_실패시_예외_발생_테스트() {
        // when & then
        assertThatThrownBy(
            () -> Creativity.builder()
                .approvedCodeSet(Set.of("CRE4302", "CRE4303"))
                .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);

        assertThatThrownBy(
            () -> Creativity.builder()
                .requiredCredit(3)
                .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }
}
