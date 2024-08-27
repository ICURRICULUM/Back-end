package icurriculum.domain.curriculum;

import icurriculum.domain.department.Department;
import icurriculum.domain.membermajor.MajorType;
import jakarta.persistence.*;
import lombok.Getter;


import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

/**
 * Embedded type
 * record type 불변객체
 * 수정 X
 */
@Embeddable
public record CurriculumDecider(
        @Enumerated(STRING)
        MajorType majorType,

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "department_id")
        Department department,

        Integer joinYear
) {
}