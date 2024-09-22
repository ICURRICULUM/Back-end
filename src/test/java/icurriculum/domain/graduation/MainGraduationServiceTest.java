package icurriculum.domain.graduation;

import icurriculum.data.컴퓨터공학과DataInitializer;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MainGraduationServiceTest {

    @Autowired
    private MainGraduationService mainGraduationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private 컴퓨터공학과DataInitializer dataInitializer;

    private Member testMember;

    @BeforeEach
    void setUp() {
        Long testMemberId = dataInitializer.getTestMemberId();
        testMember = memberRepository.findById(testMemberId).orElseThrow();
    }

    @Test
    @DisplayName("통합 테스트 - 졸업 요건 확인")
    void 통합_테스트_졸업요건확인() {
        mainGraduationService.execute(testMember);

        System.out.println("통합 테스트 실행 완료");
    }
}
