package icurriculum.domain.curriculum;

import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class CurriculumDecider {

    private MajorType majorType;

    private DepartmentName departmentName;

    private Integer joinYear;

    @Builder
    private CurriculumDecider(
        MajorType majorType,
        DepartmentName departmentName,
        Integer joinYear
    ) {
        this.majorType = majorType;
        this.departmentName = departmentName;
        this.joinYear = joinYear;

        validate();
    }

    public void validate() {
        if (majorType == null || departmentName == null || joinYear == null) {
            throw new GeneralException(ErrorStatus.CURRICULUM_DECIDER_MISSING_VALUE, this);
        }
    }

}
