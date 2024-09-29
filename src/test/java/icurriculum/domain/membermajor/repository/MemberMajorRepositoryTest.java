package icurriculum.domain.membermajor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.data.TestDataInitializer;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberMajorRepositoryTest {

    @Autowired
    private MemberMajorRepository memberMajorRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private TestDataInitializer testDataInitializer;

    private Member testMember;
    private Department department;
    private List<MemberMajor> testMemberMajorList;

    @BeforeEach
    void setUp() {
        testDataInitializer = new TestDataInitializer(
            memberRepository,
            departmentRepository,
            memberMajorRepository
        );

        testMember = testDataInitializer.initMemberData();
        department = testDataInitializer.initDepartmentData();
        testMemberMajorList = testDataInitializer.initMemberMajorData();
    }

    @Test
    @DisplayName("Member로 MemberMajor 리스트를 조회하는 테스트")
    void 멤버로_전공_리스트_조회_테스트() {
        // when
        List<MemberMajor> findMemberMajorList = memberMajorRepository.findByMember(testMember);

        // then
        assertThat(findMemberMajorList).hasSameSizeAs(testMemberMajorList)
            .containsExactlyInAnyOrderElementsOf(testMemberMajorList);
    }

    @Test
    @DisplayName("Member와 MajorType으로 MemberMajor 조회하는 테스트")
    void 멤버와_전공으로_조회_테스트() {
        // given
        MajorType majorType = MajorType.주전공;

        // when
        Optional<MemberMajor> findMemberMajor = memberMajorRepository.findByMemberAndMajorType(
            testMember, majorType);

        // then
        assertThat(findMemberMajor).isPresent();
        assertThat(findMemberMajor.get()).isEqualTo(testMemberMajorList.get(0));
    }
}
