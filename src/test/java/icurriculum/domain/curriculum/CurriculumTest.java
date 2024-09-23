package icurriculum.domain.curriculum;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.domain.curriculum.json.AlternativeCourseJson;
import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.curriculum.json.CreativityJson;
import icurriculum.domain.curriculum.json.CurriculumCodeJson;
import icurriculum.domain.curriculum.json.RequiredCreditJson;
import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.domain.membermajor.MajorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CurriculumTest {

    private Curriculum curriculum;
    private CurriculumDecider decider;
    private CoreJson coreJson;
    private SwAiJson swAiJson;
    private CreativityJson creativityJson;
    private RequiredCreditJson requiredCreditJson;
    private CurriculumCodeJson curriculumCodeJson;
    private AlternativeCourseJson alternativeCourseJson;

    @BeforeEach
    void setUp() {
        decider = new CurriculumDecider(MajorType.주전공, 컴퓨터공학과, 19);

        coreJson = new CoreJson(false, 9, emptySet(), emptyMap(), emptyMap(), emptyMap());
        swAiJson = new SwAiJson(emptySet(), emptySet(), 0, emptyMap());
        creativityJson = new CreativityJson(emptySet(), 0, emptyMap());
        requiredCreditJson = new RequiredCreditJson(130, 65, 39, 21);
        curriculumCodeJson = new CurriculumCodeJson(emptyMap(), emptyMap());
        alternativeCourseJson = new AlternativeCourseJson(emptyMap());

        curriculum = Curriculum.builder().decider(decider).coreJson(coreJson).swAiJson(swAiJson)
            .creativityJson(creativityJson).requiredCreditJson(requiredCreditJson)
            .curriculumCodeJson(curriculumCodeJson).alternativeCourseJson(alternativeCourseJson)
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
        assertThat(curriculum.getCurriculumCodeJson()).isEqualTo(curriculumCodeJson);
        assertThat(curriculum.getAlternativeCourseJson()).isEqualTo(alternativeCourseJson);
    }

}

