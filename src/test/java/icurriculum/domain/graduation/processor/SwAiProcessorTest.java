package icurriculum.domain.graduation.processor;

import icurriculum.DataInitializer;
import icurriculum.domain.curriculum.json.SwAiJson;
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

import java.util.Collections;
import java.util.List;

@SpringBootTest
class SwAiProcessorTest {

    @Autowired
    DataInitializer dataInitializer;
    @Autowired
    TakeRepository takeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberMajorRepository memberMajorRepository;

    SwAiProcessor processor = new SwAiProcessor();
    SwAiJson swAiJson;
    List<Take> swAiTakes;

    @BeforeEach
    void beforeEach() {
        Long testMemberId = dataInitializer.getTestMemberId();
        Member testMember = memberRepository.findById(testMemberId).get();
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(testMember);
        MemberMajor mainMemberMajor = MemberMajorUtils.getMainMemberMajor(memberMajors);
        List<Take> takes = takeRepository.findByMemberAndDepartment(testMember, mainMemberMajor.getDepartment());
        swAiTakes = TakeUtils.getTakesByCategory(takes, Category.SW_AI);
    }


    @Test
    @DisplayName("과목지정여부 X, 필요학점 0 판단")
    public void execute() throws Exception {
        // given
        swAiJson = new SwAiJson(false, Collections.emptySet(), 0);

        // when
        ProcessorDto.SwAiDto swAiDto = processor.execute(swAiJson, swAiTakes);

        // then
        Assertions.assertThat(swAiDto.getCompletedCredit()).isEqualTo(0);
        Assertions.assertThat(swAiDto.getRequiredCredits()).isEqualTo(0);
        Assertions.assertThat(swAiDto.isClear()).isEqualTo(true);
    }

}