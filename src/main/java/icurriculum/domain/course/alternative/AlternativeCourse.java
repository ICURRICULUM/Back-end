package icurriculum.domain.course.alternative;

import icurriculum.domain.course.Course;
import icurriculum.domain.department.Department;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 대체 과목
 */
@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class AlternativeCourse {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "alternative_course_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "alternative_id")
    private Course alternative;

    /**
     * 과마다 대체 과목이 다를 수 있음
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    public AlternativeCourse(Course course, Course alternative, Department department) {
        this.course = course;
        this.alternative = alternative;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlternativeCourse that = (AlternativeCourse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCourse(), that.getCourse()) && Objects.equals(getAlternative(), that.getAlternative()) && Objects.equals(getDepartment(), that.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCourse(), getAlternative(), getDepartment());
    }

    @Override
    public String toString() {
        return "AlternativeCourse{" +
                "id=" + id +
                ", course=" + course +
                ", alternative=" + alternative +
                ", department=" + department +
                '}';
    }
}
