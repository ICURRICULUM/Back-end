package icurriculum.domain.course.service;

import icurriculum.domain.categoryjudge.CategoryJudgeUtils;
import icurriculum.domain.course.Course;
import icurriculum.domain.course.dto.CourseConverter;
import icurriculum.domain.course.dto.CourseResponse.DetailInfoDTO;
import icurriculum.domain.course.repository.CourseRepository;

import java.util.*;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.take.Category;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository repository;
    private final CurriculumService curriculumService;

    public List<Course> getCourseListByCodeSet(Set<String> codes) {
        return repository.findByCodeSet(codes);
    }

    public DetailInfoDTO getCourse(String code, MemberMajor memberMajor) {
        Optional<Course> course = repository.findByCode(code);
        /**
         * todo : error 처리 수
         */
        if (course.isEmpty()) throw new GeneralException(ErrorStatus.COURSE_IS_NOT_VALID, this);
        Course findCourse = course.get();
        ArrayList<String> codes = new ArrayList<>();
        codes.add(code);
        Curriculum curriculum = curriculumService.getCurriculumByMemberMajor(memberMajor);
        curriculum.validate();
        Map<String, Category> judgedCodes = CategoryJudgeUtils.judge(codes, curriculum);
        return CourseConverter.toCourseDetailInfo(findCourse, judgedCodes.get(code));
    }
}
