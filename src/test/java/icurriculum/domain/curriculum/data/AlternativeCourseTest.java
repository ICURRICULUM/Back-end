package icurriculum.domain.curriculum.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AlternativeCourseTest {

    @Test
    @DisplayName("빌더를 통해 AlternativeCourse 객체를 생성할 때 기본값이 설정되는지 테스트")
    void 빌더로_객체_생성시_기본값_설정_테스트() {
        // given
        AlternativeCourse alternativeCourse = AlternativeCourse.builder().build();

        // then
        assertThat(alternativeCourse.getAlternativeCodeSet("DUMMY")).isEmpty();
    }

    @Test
    @DisplayName("대체 과목 코드 맵이 제대로 설정되고 가져올 수 있는지 테스트")
    void 대체과목코드_설정_및_가져오기_테스트() {
        // given
        Map<String, Set<String>> alternativeCourseMap = new HashMap<>();
        Set<String> alternativeCodeSet = new HashSet<>();
        alternativeCodeSet.add("ABB1236");
        alternativeCodeSet.add("ABB1237");
        alternativeCourseMap.put("ABC1234", alternativeCodeSet);

        // when
        AlternativeCourse alternativeCourse = AlternativeCourse.builder()
            .alternativeCourseCodeMap(alternativeCourseMap)
            .build();

        // then
        assertThat(alternativeCourse.getAlternativeCodeSet("ABC1234"))
            .containsExactlyInAnyOrder("ABB1236", "ABB1237");
    }

    @Test
    @DisplayName("대체 과목이 없는 경우 빈 집합을 반환하는지 테스트")
    void 대체과목이_없는경우_빈집합_반환_테스트() {
        // given
        Map<String, Set<String>> alternativeCourseMap = new HashMap<>();
        Set<String> alternativeCodeSet = new HashSet<>();
        alternativeCodeSet.add("ABB1236");
        alternativeCodeSet.add("ABB1237");
        alternativeCourseMap.put("ABC1234", alternativeCodeSet);

        AlternativeCourse alternativeCourse = AlternativeCourse.builder()
            .alternativeCourseCodeMap(alternativeCourseMap)
            .build();

        // when
        Set<String> result = alternativeCourse.getAlternativeCodeSet("NON_EXISTING_CODE");

        // then
        assertThat(result).isEmpty();
    }

}
