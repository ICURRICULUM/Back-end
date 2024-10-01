package icurriculum.domain.course;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.common.BaseRDBEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Course extends BaseRDBEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "course_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer credit;

    @Builder
    private Course(String code, String name, Integer credit) {
        this.code = code;
        this.name = name;
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(getCode(), course.getCode()) && Objects.equals(
            getName(), course.getName()) && Objects.equals(getCredit(), course.getCredit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getName(), getCredit());
    }
}
