package icurriculum.domain.course.service;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository repository;

    public List<Course> getCourseListByCodeSet(Set<String> codes) {
        return repository.findByCodeSet(codes);
    }
}
