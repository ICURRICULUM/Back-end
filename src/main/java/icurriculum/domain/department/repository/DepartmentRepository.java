package icurriculum.domain.department.repository;

import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(DepartmentName name);
}
