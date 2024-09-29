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

public class MajorRequiredTest {


    @Test
    @DisplayName("빌더를 통해 MajorRequired 객체를 생성할 때 성공 테스트")
    void 빌더로_MajorRequired_객체_생성시_성공_테스트() {
        // given
        Set<String> codeSet = Set.of("ABC1234", "ABC1235");

        MajorRequired majorRequired = MajorRequired.builder()
            .codeSet(codeSet)
            .build();

        // when & then
        assertThat(majorRequired.getCodeSet()).containsExactlyInAnyOrder("ABC1234", "ABC1235");
    }

    @Test
    @DisplayName("추가 정보를 올바르게 가져오는지 테스트")
    void 추가정보_가져오기_테스트() {
        // given
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("credits", 3);

        MajorRequired majorRequired = MajorRequired.builder()
            .codeSet(Set.of("ABC1234", "ABC1235"))
            .additionalInfoMap(additionalInfo)
            .build();

        // then
        assertThat(majorRequired.getAdditionalInfo("credits")).contains(3);
        assertThat(majorRequired.getAdditionalInfo("non_existing_key")).isEmpty();
    }

    @Test
    @DisplayName("유효성 검사 실패 시 예외 발생 테스트")
    void 유효성_검사_실패시_예외_발생_테스트() {
        // then
        assertThatThrownBy(() -> MajorRequired.builder()
            .codeSet(Set.of())
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }
}
