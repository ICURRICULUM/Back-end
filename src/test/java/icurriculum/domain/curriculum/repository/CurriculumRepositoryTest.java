package icurriculum.domain.curriculum.repository;

import icurriculum.DataInitializer;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.domain.membermajor.util.MemberMajorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class CurriculumRepositoryTest {

    @Autowired
    CurriculumRepository curriculumRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberMajorRepository memberMajorRepository;
    @Autowired
    DataInitializer dataInitializer;

    MemberMajor memberMajor;

    @BeforeEach
    void beforeEach() {
        Long memberId = dataInitializer.getTestMemberId();
        Member member = memberRepository.findById(memberId).get();
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(member);
        memberMajor = MemberMajorUtils.getMainMemberMajor(memberMajors);
    }

    @Test
    public void findByDecider() {
        // given
        CurriculumDecider decider = CurriculumDecider.createCurriculumDecider(memberMajor);
        Curriculum curriculumTestData = dataInitializer.getTestCurriculumData(memberMajor);

        // when
        Curriculum expected = curriculumRepository.findByDecider(decider)
                .orElseThrow(RuntimeException::new); // Todo 예외 추후 정의

        // then
        assertThat(curriculumTestData.getDecider()).isEqualTo(expected.getDecider());
    }

}