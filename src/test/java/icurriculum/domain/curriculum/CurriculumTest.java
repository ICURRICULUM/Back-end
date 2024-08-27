package icurriculum.domain.curriculum;

import icurriculum.domain.curriculum.json.*;
import icurriculum.domain.department.Department;
import icurriculum.domain.membermajor.MajorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

class CurriculumTest {

    private Curriculum curriculum;
    private CurriculumDecider decider;
    private CoreJson coreJson;
    private SwAiJson swAiJson;
    private CreativityJson creativityJson;
    private RequirementCreditJson requiredCreditJson;
    private CurriculumCodesJson curriculumCodesJson;
    private AlternativeCourseJson alternativeCourseJson;

    @BeforeEach
    void setUp() {
        Department department = Department.builder().name(컴퓨터공학과).build();
        decider = new CurriculumDecider(MajorType.주전공, department, 2019);

        coreJson = new CoreJson(false, 9, emptySet(), emptyMap(), emptyMap());
        swAiJson = new SwAiJson(false, emptySet(), emptySet(),0);
        creativityJson = new CreativityJson(false, emptySet(), 0);
        requiredCreditJson = new RequirementCreditJson(130, 65, 39, 21);
        curriculumCodesJson = new CurriculumCodesJson(emptyMap());
        alternativeCourseJson = new AlternativeCourseJson(emptyMap());

        curriculum = Curriculum.builder()
                .decider(decider)
                .coreJson(coreJson)
                .swAiJson(swAiJson)
                .creativityJson(creativityJson)
                .requiredCreditJson(requiredCreditJson)
                .curriculumCodesJson(curriculumCodesJson)
                .alternativeCourseJson(alternativeCourseJson)
                .build();
    }

    @Test
    @DisplayName("Curriculum 객체 생성 테스트")
    void testCurriculumCreation() {
        assertThat(curriculum).isNotNull();
        assertThat(curriculum.getDecider()).isEqualTo(decider);
        assertThat(curriculum.getCoreJson()).isEqualTo(coreJson);
        assertThat(curriculum.getSwAiJson()).isEqualTo(swAiJson);
        assertThat(curriculum.getCreativityJson()).isEqualTo(creativityJson);
        assertThat(curriculum.getRequiredCreditJson()).isEqualTo(requiredCreditJson);
        assertThat(curriculum.getCurriculumCodesJson()).isEqualTo(curriculumCodesJson);
        assertThat(curriculum.getAlternativeCourseJson()).isEqualTo(alternativeCourseJson);
    }

}

