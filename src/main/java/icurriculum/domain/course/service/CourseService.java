package icurriculum.domain.course.service;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.department.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository repository;

    public List<Course> getCoursesByCodesAndDepartment(Set<String> codes, Department department) {
        return repository.findByCodesAndDepartment(codes, department);
    }

    public Course getCourseByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(RuntimeException::new);
        /**
         * TODO 예외 추후 정의
         */
    }
}
