package icurriculum.domain.graduation.processor;

import icurriculum.DataInitializer;
import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.graduation.processor.dto.ProcessorDto;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.domain.membermajor.util.MemberMajorUtils;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import icurriculum.domain.take.util.TakeUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class CoreProcessorTest {

    @Autowired
    DataInitializer dataInitializer;
    @Autowired
    TakeRepository takeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberMajorRepository memberMajorRepository;

    CoreProcessor processor = new CoreProcessor();
    CoreJson coreJson;
    List<Take> coreTakes;

    @BeforeEach
    void beforeEach() {
        Long testMemberId = dataInitializer.getTestMemberId();
        Member testMember = memberRepository.findById(testMemberId).get();
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(testMember);
        MemberMajor mainMemberMajor = MemberMajorUtils.getMainMemberMajor(memberMajors);
        List<Take> takes = takeRepository.findByMemberAndDepartment(testMember, mainMemberMajor.getDepartment());
        coreTakes = TakeUtils.getCoreTakes(takes);
    }


    @Test
    @DisplayName("영역지정여부 X, 필요학점 9 판단")
    public void execute() throws Exception {
        // given
        coreJson = new CoreJson(false, 9, Collections.emptySet(), Collections.emptyMap());

        // when
        ProcessorDto.CoreDto coreDto = processor.execute(coreJson, coreTakes);

        // then
        Assertions.assertThat(coreDto.getCompletedCredit()).isEqualTo(9);
        Assertions.assertThat(coreDto.getRequiredCredits()).isEqualTo(9);
        Assertions.assertThat(coreDto.getUncompletedArea()).isEmpty();
        Assertions.assertThat(coreDto.isClear()).isTrue();
    }

    @Test
    @DisplayName("영역지정여부1,3,4_필요학점 9 판단")
    public void execute_영역지정된상태_성공() throws Exception {
        // given
        coreJson = new CoreJson(true, 9,
                Set.of(Category.핵심교양1, Category.핵심교양3, Category.핵심교양4), Collections.emptyMap());

        // when
        ProcessorDto.CoreDto coreDto = processor.execute(coreJson, coreTakes);

        // then
        Assertions.assertThat(coreDto.getCompletedCredit()).isEqualTo(9);
        Assertions.assertThat(coreDto.getRequiredCredits()).isEqualTo(9);
        Assertions.assertThat(coreDto.getUncompletedArea()).isEmpty();
        Assertions.assertThat(coreDto.isClear()).isTrue();
    }

    @Test
    @DisplayName("영역지정여부1,3,4,5 필요학점 12 판단_실패")
    public void execute_영역지정된상태_실패() throws Exception {
        // given
        coreJson = new CoreJson(true, 12,
                Set.of(Category.핵심교양1, Category.핵심교양3, Category.핵심교양4, Category.핵심교양5), Collections.emptyMap());

        // when
        ProcessorDto.CoreDto coreDto = processor.execute(coreJson, coreTakes);

        // then
        Assertions.assertThat(coreDto.getCompletedCredit()).isEqualTo(9);
        Assertions.assertThat(coreDto.getRequiredCredits()).isEqualTo(12);
        Assertions.assertThat(coreDto.getUncompletedArea()).contains(Category.핵심교양5);
        Assertions.assertThat(coreDto.isClear()).isFalse();
    }


}