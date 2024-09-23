package icurriculum.domain.curriculum;

import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class CurriculumDecider {

    private MajorType majorType;

    private DepartmentName departmentName;

    private Integer joinYear;
}
