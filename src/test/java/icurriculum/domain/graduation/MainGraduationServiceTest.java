package icurriculum.domain.graduation;

import icurriculum.data.컴공19BData;
import icurriculum.domain.graduation.service.module.MainGraduationService;
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
//@Import(컴퓨터공학과DataInitializer.class)
//@Import(컴공19LData.class)
//@Import(컴공20KData.class)
//@Import(컴공18LData.class)
//@Import(컴공22LData.class)
//@Import(컴공22LSJata.class)
//@Import(컴공22SData.class)
//@Import(컴공22OData.class)
@Import(컴공19BData.class)
class MainGraduationServiceTest {

    @Autowired
    private MainGraduationService mainGraduationService;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = memberRepository
            .findById(1L)
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
