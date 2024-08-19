package icurriculum.domain.course;

import icurriculum.domain.course.alternative.AlternativeCourse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String code;

    @Column(name = "course_name")
    private String name;

    private Integer credit;

    @OneToMany(mappedBy = "course", cascade = ALL, orphanRemoval = true)
    private List<AlternativeCourse> alternativeCourses = new ArrayList<>();

    @Builder
    public Course(String code, String name, Integer credit) {
        this.code = code;
        this.name = name;
        this.credit = credit;
    }

    /**
     * 테스트용 삭제 예정
     */
    public void setAlternativeCourses(List<AlternativeCourse> alternativeCourses) {
        this.alternativeCourses = alternativeCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(getId(), course.getId()) && Objects.equals(getCode(), course.getCode()) && Objects.equals(getName(), course.getName()) && Objects.equals(getCredit(), course.getCredit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode(), getName(), getCredit());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                '}';
    }
}
