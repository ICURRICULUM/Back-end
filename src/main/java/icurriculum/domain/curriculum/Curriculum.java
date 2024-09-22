package icurriculum.domain.curriculum;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.common.BaseEntity;
import icurriculum.domain.curriculum.json.AlternativeCoursesJson;
import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.curriculum.json.CreativityJson;
import icurriculum.domain.curriculum.json.CurriculumCodesJson;
import icurriculum.domain.curriculum.json.RequiredCreditJson;
import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.domain.curriculum.json.converter.AlternativeCourseJsonConverter;
import icurriculum.domain.curriculum.json.converter.CoreJsonConverter;
import icurriculum.domain.curriculum.json.converter.CreativityJsonConverter;
import icurriculum.domain.curriculum.json.converter.CurriculumCodesJsonConverter;
import icurriculum.domain.curriculum.json.converter.RequiredCreditJsonConverter;
import icurriculum.domain.curriculum.json.converter.SwAiJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "unique_curriculum_decider",
            columnNames = {"major_type", "department_name", "join_year"}
        )
    }
)
public class Curriculum extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "curriculum_id")
    private Long id;

    @Embedded
    private CurriculumDecider decider;

    /**
     * 핵심교양, SW_AI, 창의, 필수이수학점, 교과과정, 대체과목
     */

    @Convert(converter = CoreJsonConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private CoreJson coreJson;

    @Convert(converter = SwAiJsonConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private SwAiJson swAiJson;

    @Convert(converter = CreativityJsonConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private CreativityJson creativityJson;

    @Convert(converter = RequiredCreditJsonConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private RequiredCreditJson requiredCreditJson;

    @Convert(converter = CurriculumCodesJsonConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private CurriculumCodesJson curriculumCodesJson;

    @Convert(converter = AlternativeCourseJsonConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private AlternativeCoursesJson alternativeCoursesJson;

    @Builder
    public Curriculum(CurriculumDecider decider, CoreJson coreJson, SwAiJson swAiJson,
        CreativityJson creativityJson, RequiredCreditJson requiredCreditJson,
        CurriculumCodesJson curriculumCodesJson, AlternativeCoursesJson alternativeCoursesJson) {
        this.decider = decider;
        this.coreJson = coreJson;
        this.swAiJson = swAiJson;
        this.creativityJson = creativityJson;
        this.requiredCreditJson = requiredCreditJson;
        this.curriculumCodesJson = curriculumCodesJson;
        this.alternativeCoursesJson = alternativeCoursesJson;
    }

}
