package icurriculum.domain.curriculum;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import jakarta.persistence.*;

import static jakarta.persistence.EnumType.STRING;

@Embeddable
public record CurriculumDecider(
    @Enumerated(STRING)
    @Column(nullable = false)
    MajorType majorType,
    @Enumerated(STRING)
    @Column(nullable = false)
    DepartmentName departmentName,
    @Column(nullable = false)
    Integer joinYear
) {

}