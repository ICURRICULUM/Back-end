package icurriculum.domain.curriculum.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequiredCreditTest {

    @Test
    @DisplayName("빌더를 통해 RequiredCredit 객체를 생성할 때 필드가 올바르게 설정되는지 테스트")
    void 빌더로_RequiredCredit_객체_생성시_필드_설정_테스트() {
        // given
        RequiredCredit requiredCredit = RequiredCredit.builder()
            .totalNeedCredit(130)
            .singleNeedCredit(65)
            .secondNeedCredit(39)
            .minorNeedCredit(21)
            .build();

        // then
        assertThat(requiredCredit.getTotalNeedCredit()).isEqualTo(130);
        assertThat(requiredCredit.getSingleNeedCredit()).isEqualTo(65);
        assertThat(requiredCredit.getSecondNeedCredit()).isEqualTo(39);
        assertThat(requiredCredit.getMinorNeedCredit()).isEqualTo(21);
    }

    @Test
    @DisplayName("필수 이수 학점 중 누락 데이터 발생 시 유효성 검사 실패 테스트")
    void 필수이수학점_유효성_검사_실패_테스트() {
        // when & then
        assertThatThrownBy(() -> RequiredCredit.builder()
            .totalNeedCredit(130)
            .secondNeedCredit(39)
            .minorNeedCredit(21)
            .build())
            .isInstanceOf(GeneralException.class)
            .hasFieldOrPropertyWithValue("errorStatus", ErrorStatus.CURRICULUM_MISSING_VALUE);
    }
}
