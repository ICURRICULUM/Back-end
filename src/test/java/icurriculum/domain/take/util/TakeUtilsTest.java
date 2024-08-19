package icurriculum.domain.take.util;

import icurriculum.DataInitializer;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.domain.membermajor.util.MemberMajorUtils;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static icurriculum.domain.take.Category.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
class TakeUtilsTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TakeRepository takeRepository;
    @Autowired
    DataInitializer dataInitializer;
    @Autowired
    MemberMajorRepository memberMajorRepository;


    @Test
    @DisplayName("영역 별 수강 내역 조회 서비스")
    public void getTakeMapForCategory() {
        // given
        Member testMember = memberRepository.findById(dataInitializer.getTestMemberId()).get();
        List<Take> actualTakes = dataInitializer.getTakesOnlyData(testMember);

        // when
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(testMember);
        MemberMajor mainMemberMajor = MemberMajorUtils.getMainMemberMajor(memberMajors);
        List<Take> expectedTakes = takeRepository.findByMemberAndDepartment(testMember, mainMemberMajor.getDepartment());

        // then
        checkValidSizeForCategory(actualTakes, expectedTakes, 전공필수);
        checkValidSizeForCategory(actualTakes, expectedTakes, 전공선택);
        checkValidSizeForCategory(actualTakes, expectedTakes, 교양필수);
        checkValidSizeForCategory(actualTakes, expectedTakes, 교양선택);
        checkValidSizeForCategory(actualTakes, expectedTakes, SW_AI);
        checkValidSizeForCategory(actualTakes, expectedTakes, 창의);
        checkValidSizeForCategory(actualTakes, expectedTakes, 핵심교양1);
        checkValidSizeForCategory(actualTakes, expectedTakes, 핵심교양2);
        checkValidSizeForCategory(actualTakes, expectedTakes, 핵심교양3);
        checkValidSizeForCategory(actualTakes, expectedTakes, 핵심교양4);
        checkValidSizeForCategory(actualTakes, expectedTakes, 핵심교양5);
        checkValidSizeForCategory(actualTakes, expectedTakes, 핵심교양6);
    }

    void checkValidSizeForCategory(List<Take> actualTakes, List<Take> expectedTakes, Category category) {
        int actualSize = TakeUtils.getTakesByCategory(actualTakes, category).size();
        int expectedSize = TakeUtils.getTakesByCategory(expectedTakes, category).size();
        assertThat(actualSize).isEqualTo(expectedSize);
    }


}