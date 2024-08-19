package icurriculum.domain.department;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Enumerated(STRING)
    @Column(name = "department_name")
    private DepartmentName name;

    @Builder
    public Department(DepartmentName name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(getId(), that.getId()) && getName() == that.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
