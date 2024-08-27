package icurriculum.domain.graduation.processor;

import icurriculum.data.컴퓨터공학과DataInitializer;
import icurriculum.domain.curriculum.json.CreativityJson;
import icurriculum.domain.graduation.processor.dto.ProcessorDto;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
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
class CreativeProcessorTest {

    @Autowired
    컴퓨터공학과DataInitializer 컴퓨터공학과DataInitializer;
    @Autowired
    TakeRepository takeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberMajorRepository memberMajorRepository;

    CreativeProcessor processor = new CreativeProcessor();
    CreativityJson creativityJson;
    List<Take> creativityTakes;

    @BeforeEach
    void beforeEach() {
        Long testMemberId = 컴퓨터공학과DataInitializer.getTestMemberId();
        Member testMember = memberRepository.findById(testMemberId).get();
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(testMember);
        List<Take> takes = takeRepository.findByMember(testMember);
        creativityTakes = TakeUtils.getTakesByCategory(takes, Category.창의);
    }


    @Test
    @DisplayName("과목지정여부 X, 필요학점 0 판단")
    public void execute() throws Exception {
        // given
        creativityJson = new CreativityJson(false, Collections.emptySet(), 0);

        // when
        ProcessorDto.CreativeDto creativeDto = processor.execute(creativityJson, creativityTakes);

        // then
        Assertions.assertThat(creativeDto.getCompletedCredit()).isEqualTo(0);
        Assertions.assertThat(creativeDto.getRequiredCredits()).isEqualTo(0);
        Assertions.assertThat(creativeDto.isClear()).isEqualTo(true);
    }

    @Test
    @DisplayName("과목지정여부 X, 필요학점 3 판단")
    public void execute_fail() throws Exception {
        // given
        creativityJson = new CreativityJson(false, Collections.emptySet(), 3);

        // when
        ProcessorDto.CreativeDto creativeDto = processor.execute(creativityJson, creativityTakes);

        // then
        Assertions.assertThat(creativeDto.getCompletedCredit()).isEqualTo(0);
        Assertions.assertThat(creativeDto.getRequiredCredits()).isEqualTo(3);
        Assertions.assertThat(creativeDto.isClear()).isEqualTo(false);
    }

}