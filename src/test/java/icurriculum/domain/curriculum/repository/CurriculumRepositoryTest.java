package icurriculum.domain.curriculum.repository;

import icurriculum.data.컴퓨터공학과DataInitializer;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.json.*;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.curriculum.util.MajorToDeciderConverter;
import icurriculum.domain.take.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(컴퓨터공학과DataInitializer.class)
class CurriculumRepositoryTest {

    @Autowired
    CurriculumRepository curriculumRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    컴퓨터공학과DataInitializer 컴퓨터공학과DataInitializer;

    Curriculum testCurriculum;
    CurriculumDecider testDecider;
    CurriculumDecider anotherDecider;

    Department 컴퓨터공학과;

    @BeforeEach
    void setUp() {
        Member member = 컴퓨터공학과DataInitializer.getMemberData();
        컴퓨터공학과 = 컴퓨터공학과DataInitializer.getDepartmentData();
        Department 전기공학과 = new Department(DepartmentName.전기공학과);

        memberRepository.save(member);
        departmentRepository.save(컴퓨터공학과);
        departmentRepository.save(전기공학과);

        MemberMajor memberMajor = 컴퓨터공학과DataInitializer.getMemberMajorData(member, 컴퓨터공학과);
        testDecider = MajorToDeciderConverter.toDecider(memberMajor);
        anotherDecider = new CurriculumDecider(
                MajorType.복수전공,
                전기공학과,
                2019
        );

        testCurriculum = 컴퓨터공학과DataInitializer.getCurriculumData(memberMajor);

        curriculumRepository.save(testCurriculum);
    }

    @Test
    @DisplayName("findByDecider 메서드로 특정 CurriculumDecider에 해당하는 Curriculum을 찾는다.")
    void findByDecider_shouldReturnCurriculumForGivenDecider() {
        // when
        Optional<Curriculum> foundCurriculum = curriculumRepository.findByDecider(testDecider);

        // then
        assertThat(foundCurriculum).isPresent();
        assertThat(foundCurriculum.get()).isEqualTo(testCurriculum);
    }

    @Test
    @DisplayName("findByDecider 메서드로 존재하지 않는 CurriculumDecider를 조회할 때 빈 Optional을 반환한다.")
    void findByDecider_shouldReturnEmptyOptionalForNonExistingDecider() {
        // when
        Optional<Curriculum> foundCurriculum = curriculumRepository.findByDecider(anotherDecider);

        // then
        assertThat(foundCurriculum).isNotPresent();
    }

    @Test
    @DisplayName("findByDeciderIn 메서드로 여러 CurriculumDecider에 해당하는 Curriculum 리스트를 찾는다.")
    void findByDeciderIn_shouldReturnCurriculumListForGivenDeciders() {
        // given
        Curriculum 전기공학과_Curriculum = getCurriculumData(anotherDecider);
        curriculumRepository.save(전기공학과_Curriculum);
        List<CurriculumDecider> deciders = List.of(testDecider, anotherDecider);

        // when
        List<Curriculum> foundCurriculums = curriculumRepository.findByDeciderIn(deciders);

        // Then
        assertThat(foundCurriculums).hasSize(2);
        assertThat(foundCurriculums).containsExactlyInAnyOrder(testCurriculum, 전기공학과_Curriculum);
    }

    @Test
    @DisplayName("findByDeciderIn 메서드로 존재하지 않는 CurriculumDecider 리스트를 조회할 때 빈 리스트를 반환한다.")
    void findByDeciderIn_shouldReturnEmptyListForNonExistingDeciders() {
        // given
        CurriculumDecider nonExistingDecider = new CurriculumDecider(
                MajorType.부전공,
                컴퓨터공학과,
                2022
        );

        List<CurriculumDecider> nonExistingDeciders = List.of(nonExistingDecider);

        // when
        List<Curriculum> foundCurriculums = curriculumRepository.findByDeciderIn(nonExistingDeciders);

        // then
        assertThat(foundCurriculums).isEmpty();
    }

    Curriculum getCurriculumData(CurriculumDecider decider) {
        return Curriculum.builder()
                .decider(decider)
                .coreJson(컴퓨터공학과DataInitializer.getCoreJsonData())
                .swAiJson(컴퓨터공학과DataInitializer.getSwAiJsonData())
                .creativityJson(컴퓨터공학과DataInitializer.getCreativityJsonData())
                .requiredCreditJson(컴퓨터공학과DataInitializer.getRequirementCreditJsonData())
                .curriculumCodesJson(컴퓨터공학과DataInitializer.getTestCurriculumCodesJsonData())
                .alternativeCourseJson(컴퓨터공학과DataInitializer.getAlternativeCourseJsonData())
                .build();
    }
}
