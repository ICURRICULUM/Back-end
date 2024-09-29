package icurriculum.domain.graduation;

import icurriculum.data.컴퓨터공학과DataInitializer;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "de.flapdoodle.mongodb.embedded.version=5.0.5")
@Transactional
@Slf4j
@Import(컴퓨터공학과DataInitializer.class)
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
        dataInitializer.init();
        testMember = memberRepository
            .findById(dataInitializer.getTestMemberId())
            .orElseThrow();
    }

    @Test
    @DisplayName("통합 테스트 - 졸업 요건 확인")
    void 통합_테스트_졸업요건확인() {
        // 실행
        mainGraduationService.execute(testMember);
        log.info("통합 테스트 실행 완료");
    }
}
