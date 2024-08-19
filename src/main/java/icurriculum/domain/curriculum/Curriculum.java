package icurriculum.domain.curriculum;

import icurriculum.domain.curriculum.json.*;
import icurriculum.domain.curriculum.json.converter.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Curriculum {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "curriculum_id")
    private Long id;

    @Embedded
    private CurriculumDecider decider;

    /**
     * - 핵심교양
     * - SW_AI
     * - 창의
     * - 필수_이수학점
     * - 교과과정
     */

    @Convert(converter = CoreJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private CoreJson coreJson;

    @Convert(converter = SwAiJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private SwAiJson swAiJson;

    @Convert(converter = CreativityJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private CreativityJson creativityJson;

    @Convert(converter = RequirementCreditJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private RequirementCreditJson requiredCreditJson;

    @Convert(converter = CurriculumCodesJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private CurriculumCodesJson curriculumCodesJson;

    @Builder
    public Curriculum(CurriculumDecider decider, CoreJson coreJson, SwAiJson swAiJson, CreativityJson creativityJson, RequirementCreditJson requiredCreditJson, CurriculumCodesJson curriculumCodesJson) {
        this.decider = decider;
        this.coreJson = coreJson;
        this.swAiJson = swAiJson;
        this.creativityJson = creativityJson;
        this.requiredCreditJson = requiredCreditJson;
        this.curriculumCodesJson = curriculumCodesJson;
    }
}
