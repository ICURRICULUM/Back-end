package icurriculum.domain.graduation;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.json.CurriculumCodesJson;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.graduation.processor.config.ProcessorCategory;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.GeneralRequirement;
import icurriculum.domain.membermajor.service.MemberMajorService;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.Category;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.*;
import icurriculum.domain.take.service.TakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static icurriculum.domain.graduation.processor.util.ProcessorUtils.*;
import static icurriculum.domain.membermajor.util.MemberMajorUtils.*;
import static icurriculum.domain.take.util.TakeUtils.*;


/**
 * 졸업요건 확인 클래스
 */


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GraduationService {

    private final TakeService takeService;
    private final MemberMajorService memberMajorService;
    private final CurriculumService curriculumService;
    private final CourseService courseService;

    private final Map<ProcessorCategory, Processor<?, ?>> processorMap;


    public void execute(Member member) {

        /**
         * 회원 전공 상태 List 가져온다.
         */
        List<MemberMajor> memberMajors = memberMajorService.getMemberMajorsByMember(member);

        /**
         * 회원의 주전공에 대한 졸업요건을 가져온다.
         */
        MemberMajor mainMemberMajor = getMainMemberMajor(memberMajors);
        CurriculumDecider mainCurriculumDecider = CurriculumDecider.createCurriculumDecider(mainMemberMajor);
        Curriculum mainCurriculum = curriculumService.getCurriculumByDecider(mainCurriculumDecider);

        /**
         * 수강내역을 가져온다.
         */
        List<Take> takes = takeService.getTakesByMemberAndDepartment(member, mainMemberMajor.getDepartment());



        /**
         * 핵심교양, SW_AI, 창의 처리
         */
        getProcessorByCategory(processorMap, ProcessorCategory.핵심교양)
                .execute(mainCurriculum.getCoreJson(), getCoreTakes(takes));
        getProcessorByCategory(processorMap, ProcessorCategory.SW_AI)
                .execute(mainCurriculum.getSwAiJson(), getTakesByCategory(takes, Category.SW_AI));
        getProcessorByCategory(processorMap, ProcessorCategory.창의)
                .execute(mainCurriculum.getCreativityJson(), getTakesByCategory(takes, Category.창의));


        /**
         * 주전공 관련 교양필수 처리
         */
        CurriculumCodesJson curriculumCodes = mainCurriculum.getCurriculumCodesJson();
        Set<String> codes = curriculumCodes.findByCategory(Category.교양필수);
        List<Course> generalRequirementCourses = courseService.getCoursesByCodesAndDepartment(codes, mainMemberMajor.getDepartment());
        GeneralRequirement generalRequirement = new GeneralRequirement(generalRequirementCourses, mainMemberMajor.getDepartment().getName());





        getProcessorByCategory(processorMap, ProcessorCategory.교양필수)
                .execute(generalRequirement, getTakesByCategory(takes, Category.교양필수));

        // 6. 전공 프로세스 진행
        전공_프로세스_진행();
    }


    private void 전공_프로세스_진행() {

    }


}
