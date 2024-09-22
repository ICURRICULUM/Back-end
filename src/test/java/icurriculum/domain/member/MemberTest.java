package icurriculum.domain.member;

import icurriculum.data.TestDataInitializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static icurriculum.data.TestDataInitializer.*;
import static org.assertj.core.api.Assertions.*;

class MemberTest {

    private final TestDataInitializer dataInitializer = new TestDataInitializer();

    @Test
    @DisplayName("Member 객체가 정상적으로 생성되는지 테스트")
    void 멤버객체생성_테스트() {
        // given
        Member member = dataInitializer.getMemberData();

        // when
        String name = member.getName();
        Integer joinYear = member.getJoinYear();
        RoleType role = member.getRole();

        // then
        assertThat(name).isEqualTo(MEMBER_NAME);
        assertThat(joinYear).isEqualTo(JOIN_YEAR);
        assertThat(role).isEqualTo(ROLE);
    }


}

