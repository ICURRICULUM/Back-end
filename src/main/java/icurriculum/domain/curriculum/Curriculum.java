package icurriculum.domain.curriculum;

import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.curriculum.json.AlternativeCourseJson;
import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.curriculum.json.CreativityJson;
import icurriculum.domain.curriculum.json.CurriculumCodeJson;
import icurriculum.domain.curriculum.json.RequiredCreditJson;
import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.global.common.BaseMongoEntity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "curriculums")
@NoArgsConstructor(access = PROTECTED)
@CompoundIndexes({
    @CompoundIndex(
        name = "uniqueCurriculumDecider",
        def = "{'decider.majorType': 1, 'decider.departmentName': 1, 'decider.joinYear': 1}",
        unique = true)
})
@Getter
@ToString
public class Curriculum extends BaseMongoEntity {

    @Id
    private String id;

    private CurriculumDecider decider;

    /*
     * 핵심교양, SW_AI, 창의, 필수이수학점, 교과과정, 대체과목
     */

    private CoreJson coreJson;
    private SwAiJson swAiJson;
    private CreativityJson creativityJson;
    private RequiredCreditJson requiredCreditJson;
    private CurriculumCodeJson curriculumCodeJson;
    private AlternativeCourseJson alternativeCourseJson;

    @Builder
    public Curriculum(CurriculumDecider decider, CoreJson coreJson, SwAiJson swAiJson,
        CreativityJson creativityJson, RequiredCreditJson requiredCreditJson,
        CurriculumCodeJson curriculumCodeJson, AlternativeCourseJson alternativeCourseJson) {
        this.decider = decider;
        this.coreJson = coreJson;
        this.swAiJson = swAiJson;
        this.creativityJson = creativityJson;
        this.requiredCreditJson = requiredCreditJson;
        this.curriculumCodeJson = curriculumCodeJson;
        this.alternativeCourseJson = alternativeCourseJson;
    }

}
