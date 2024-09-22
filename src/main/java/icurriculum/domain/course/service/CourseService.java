package icurriculum.domain.course.service;

import icurriculum.domain.categoryjudge.CategoryJudgeUtils;
import icurriculum.domain.categoryjudge.CategoryJudgeUtilsImpl;
import icurriculum.domain.course.Course;
import icurriculum.domain.course.dto.CourseSearchResponseDTO;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.service.MemberMajorService;
import icurriculum.domain.take.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository repository;
    private final CurriculumService curriculumService;
    private final CategoryJudgeUtils categoryJudgeUtils;
    private final MemberMajorService memberMajorService;

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

    public CourseSearchResponseDTO getCourse(Member member, String code) {
        List<MemberMajor> majorsByMember = memberMajorService.findMajorsByMember(member);
        Curriculum curriculum = curriculumService.findCurriculumByMemberMajor(majorsByMember);
        Optional<Course> course = repository.findByCode(code);
        if(course.isEmpty()) throw new RuntimeException("해당 과목이 없습니다.");
        Course findCourse = course.get();
        ArrayList<String> codes = new ArrayList<>();
        codes.add(code);
        Map<String, Category> judgedCodes = categoryJudgeUtils.judge(codes, curriculum);
        return CourseSearchResponseDTO.builder()
                .courseId(findCourse.getId())
                .name(findCourse.getName())
                .credit(findCourse.getCredit())
                .category(judgedCodes.get(code).toString())
                .build();
    }
}
