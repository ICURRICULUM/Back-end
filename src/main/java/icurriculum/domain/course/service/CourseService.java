package icurriculum.domain.course.service;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
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

    public List<Course> findCoursesByCodes(Set<String> codes) {
        return repository.findByCodes(codes);
    }

    public Course findCourseByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(RuntimeException::new);
        /**
         * TODO 예외 추후 정의
         */
    }
}
