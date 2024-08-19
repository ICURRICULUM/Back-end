package icurriculum.domain.take.repository;

import icurriculum.DataInitializer;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.domain.membermajor.util.MemberMajorUtils;
import icurriculum.domain.take.Take;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TakeRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TakeRepository takeRepository;
    @Autowired
    MemberMajorRepository memberMajorRepository;
    @Autowired
    DataInitializer dataInitializer;



    @Test
    @DisplayName("수강목록 조회 확인")
    public void getTakeMapForCategory() throws Exception {
        // given
        Long testMemberId = dataInitializer.getTestMemberId();
        Member testMember = memberRepository.findById(testMemberId).get();
        List<Take> actualTakes = dataInitializer.getTakesOnlyData(testMember);


        // when
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(testMember);
        MemberMajor mainMemberMajor = MemberMajorUtils.getMainMemberMajor(memberMajors);
        List<Take> expectedTakes = takeRepository.findByMemberAndDepartment(testMember, mainMemberMajor.getDepartment());

        // then
        assertThat(actualTakes.size()).isEqualTo(expectedTakes.size());
    }

}