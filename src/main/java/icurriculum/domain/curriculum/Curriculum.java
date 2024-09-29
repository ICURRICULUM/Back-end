package icurriculum.domain.curriculum;

import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.common.BaseMongoEntity;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
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
     * - 핵심교양
     * - SW_AI
     * - 창의
     * - 전공필수
     * - 전공선택
     * - 교양필수
     * - 필수이수학점
     * - 대체과목
     */

    private Core core;
    private SwAi swAi;
    private Creativity creativity;
    private MajorRequired majorRequired;
    private MajorSelect majorSelect;
    private GeneralRequired generalRequired;
    private RequiredCredit requiredCredit;
    private AlternativeCourse alternativeCourse;

    @Builder
    private Curriculum(
        CurriculumDecider decider,
        Core core, SwAi swAi,
        Creativity creativity, MajorRequired majorRequired,
        MajorSelect majorSelect, GeneralRequired generalRequired,
        RequiredCredit requiredCredit, AlternativeCourse alternativeCourse
    ) {
        this.decider = decider;
        this.core = core;
        this.swAi = swAi;
        this.creativity = creativity;
        this.majorRequired = majorRequired;
        this.majorSelect = majorSelect;
        this.generalRequired = generalRequired;
        this.requiredCredit = requiredCredit;
        this.alternativeCourse = alternativeCourse;

        validate();
    }

    /*
     * [validate]
     *
     * MongoDB repository로 데이터를 조회할 때,
     * DB에 없는 데이터는 null로 셋팅된다. -> NullPointException 발생 가능성이 높다.
     *
     * 따라서 필수값이 빠져있으면 에러를 터트린다.
     * 필수값이 아니라면, NullPointException을 방지하기 위해 빈 데이터를 셋팅한다.
     */

    public void validate() {
        if (decider == null ||
            core == null || swAi == null ||
            creativity == null || majorRequired == null ||
            majorSelect == null || generalRequired == null ||
            requiredCredit == null || alternativeCourse == null
        ) {
            throw new GeneralException(ErrorStatus.CURRICULUM_MISSING_VALUE, this);
        }

        decider.validate();
        core.validate();
        swAi.validate();
        creativity.validate();
        majorRequired.validate();
        majorSelect.validate();
        generalRequired.validate();
        requiredCredit.validate();
    }

}
