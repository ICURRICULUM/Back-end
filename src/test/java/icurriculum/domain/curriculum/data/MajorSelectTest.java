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

public class MajorSelectTest {

    @Test
    @DisplayName("빌더를 통해 MajorSelect 객체를 생성할 때 성공 테스트")
    void 빌더로_MajorSelect_객체_생성시_성공_테스트() {
        // given
        MajorSelect majorSelect = MajorSelect.builder()
            .codeSet(Set.of("SUBJ3001", "SUBJ3002"))
            .build();

        // then
        assertThat(majorSelect.getCodeSet()).containsExactlyInAnyOrder("SUBJ3001", "SUBJ3002");
        assertThat(majorSelect.getAdditionalInfo("key")).isEmpty();
    }

    @Test
    @DisplayName("추가 정보를 올바르게 가져오는지 테스트")
    void 추가정보_가져오기_테스트() {
        // given
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("professor", "Dr. Lee");
        additionalInfo.put("semester", "Fall 2024");

        MajorSelect majorSelect = MajorSelect.builder()
            .additionalInfoMap(additionalInfo)
            .codeSet(Set.of("SUBJ3001", "SUBJ3002"))
            .build();

        // then
        assertThat(majorSelect.getAdditionalInfo("professor")).contains("Dr. Lee");
        assertThat(majorSelect.getAdditionalInfo("semester")).contains("Fall 2024");
        assertThat(majorSelect.getAdditionalInfo("non_existing_key")).isEmpty();
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트")
    void 유효성_검사_실패시_예외_발생_테스트() {
        // when & then
        assertThatThrownBy(() -> MajorSelect.builder()
            .codeSet(Set.of())
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }
}
