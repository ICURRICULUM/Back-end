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

public class GeneralRequiredTest {

    @Test
    @DisplayName("빌더를 통해 GeneralRequired 객체를 생성할 때 성공 테스트")
    void 빌더로_GeneralRequired_객체_생성시_성공테스트() {
        // given
        Set<String> codeSet = Set.of("ABC1001", "ABC1002");

        GeneralRequired generalRequired = GeneralRequired.builder()
            .codeSet(codeSet)
            .build();

        // when & then
        assertThat(generalRequired.getCodeSet()).containsExactlyInAnyOrder("ABC1001", "ABC1002");
    }

    @Test
    @DisplayName("추가 정보를 올바르게 가져오는지 테스트")
    void 추가정보_가져오기_테스트() {
        // given
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("a", "b");
        additionalInfo.put("c", 1);

        // when
        GeneralRequired generalRequired = GeneralRequired.builder()
            .codeSet(Set.of("CRE4302", "CRE4303"))
            .additionalInfoMap(additionalInfo)
            .build();

        // then
        assertThat(generalRequired.getAdditionalInfo("a")).contains("b");
        assertThat(generalRequired.getAdditionalInfo("c")).contains(1);
        assertThat(generalRequired.getAdditionalInfo("nonExistingKey")).isEmpty();
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트")
    void 유효성_검사_실패시_예외_발생_테스트() {

        // then
        assertThatThrownBy(() -> GeneralRequired.builder()
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }
}
