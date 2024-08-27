package icurriculum.domain.take.repository;

import icurriculum.data.컴퓨터공학과DataInitializer;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.take.Take;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(컴퓨터공학과DataInitializer.class)
public class TakeRepositoryTest {

    @Autowired
    private TakeRepository takeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private 컴퓨터공학과DataInitializer 컴퓨터공학과DataInitializer;

    private Member testMember;

    @BeforeEach
    void setUp() {
        컴퓨터공학과DataInitializer.init();
        testMember = memberRepository.findById(컴퓨터공학과DataInitializer.getTestMemberId())
                .orElseThrow(() -> new RuntimeException("테스트 멤버 존재 X"));
    }

    @Test
    @DisplayName("findByMember, fetch join 성공")
    void findByMember_shouldReturnTakesForMember() {
        // given
        int actualDataSize = 컴퓨터공학과DataInitializer.getTakesData(testMember).size();

        // when
        List<Take> takes = takeRepository.findByMember(testMember);

        // then
        assertThat(takes).isNotEmpty();
        assertThat(takes).hasSize(actualDataSize);

        for (Take take : takes) {
            assertThat(take.getMember()).isEqualTo(testMember);
            assertThat(take.getEffectiveCourse()).isNotNull();
            assertThat(take.getMember()).isEqualTo(testMember);
        }
    }
}
