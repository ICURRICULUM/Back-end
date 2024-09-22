package icurriculum.domain.take.repository;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.data.TestDataInitializer;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Take;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TakeRepositoryTest {

    @Autowired
    private TakeRepository takeRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CourseRepository courseRepository;

    private TestDataInitializer testDataInitializer;

    private Member testMember;
    private List<Take> testTakeList;

    @BeforeEach
    void setUp() {
        testDataInitializer = new TestDataInitializer(memberRepository, courseRepository,
            takeRepository);
        testMember = testDataInitializer.initMemberData();
        testDataInitializer.initCourseData();
        testTakeList = testDataInitializer.initTakeData();
    }

    @Test
    @DisplayName("Member로 Take 리스트를 조회하는 테스트")
    void 멤버로_수강_리스트_조회_테스트() {
        // when
        List<Take> findTakeList = takeRepository.findByMember(testMember);

        // then
        assertThat(findTakeList).hasSameSizeAs(testTakeList)
            .containsExactlyInAnyOrderElementsOf(testTakeList); // 리스트 내 요소 동일
    }

    @Test
    @DisplayName("Member와 MajorType으로 Take 리스트를 조회하는 테스트")
    void 멤버와_전공으로_수강_리스트_조회_테스트() {
        // given
        MajorType majorType = MajorType.주전공;

        // when
        List<Take> findTakeList = takeRepository.findByMemberAndMajorType(testMember, majorType);

        // then
        assertThat(findTakeList).hasSameSizeAs(testTakeList)
            .containsExactlyInAnyOrderElementsOf(testTakeList); // 리스트 내 요소 동일
    }
}
