package icurriculum.domain.graduation;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.json.CurriculumCodesJson;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.config.ProcessorCategory;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.service.MemberMajorService;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.service.TakeService;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.ProcessorUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/*
 * 주전공 졸업요건 확인 클래스
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainGraduationService {

    private final TakeService takeService;
    private final MemberMajorService memberMajorService;
    private final CurriculumService curriculumService;
    private final CourseService courseService;

    private final Map<ProcessorCategory, Processor<?, ?>> processorMap;

    public void execute(Member member) {

        /*
         * 주전공 수강 List 가져온다.
         */
        LinkedList<Take> allTakeList = takeService
            .getTakeListByMemberAndMajorType(member, MajorType.주전공);

        /*
         * 회원의 주전공 상태 가져온다.
         */
        MemberMajor mainMemberMajor = memberMajorService
            .getMemberMajorByMemberAndMajorType(member, MajorType.주전공);

        /*
         * 주전공 졸업요건(Curriculum)을 가져온다.
         */
        Curriculum mainCurriculum = curriculumService.getCurriculumByMemberMajor(mainMemberMajor);

        /*
         * SW_AI 실행
         */
        ProcessorResponse.SwAiDTO swAiDTO = executeProcessor(
            ProcessorCategory.SW_AI,
            mainCurriculum.getSwAiJson(),
            ProcessorRequest.SwAiDTO.class,
            mainCurriculum.getAlternativeCoursesJson().getAlternativeCourseMap(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("swAiDTO = " + swAiDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        /*
         * 창의 실행
         */
        ProcessorResponse.CreativityDTO creativityDTO = executeProcessor(
            ProcessorCategory.창의,
            mainCurriculum.getCreativityJson(),
            ProcessorRequest.CreativityDTO.class,
            mainCurriculum.getAlternativeCoursesJson().getAlternativeCourseMap(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("creativityDTO = " + creativityDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        /*
         * 핵심교양 실행
         */
        ProcessorResponse.CoreDTO coreDTO = executeProcessor(
            ProcessorCategory.핵심교양,
            mainCurriculum.getCoreJson(),
            ProcessorRequest.CoreDTO.class,
            mainCurriculum.getAlternativeCoursesJson().getAlternativeCourseMap(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("coreDTO = " + coreDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        /*
         * 주전공-교양필수 실행
         */
        List<Course> generalRequiredCourseList = getEssentialCourseListByCategory(
            mainCurriculum.getCurriculumCodesJson(), Category.교양필수);

        ProcessorResponse.GeneralRequiredDTO generalRequiredDTO = executeProcessor(
            ProcessorCategory.교양필수,
            generalRequiredCourseList,
            ProcessorRequest.GeneralRequiredDTO.class,
            mainCurriculum.getAlternativeCoursesJson().getAlternativeCourseMap(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("generalRequiredDTO = " + generalRequiredDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        /*
         * 주전공-전공필수 실행
         */
        List<Course> majorRequiredCourseList = getEssentialCourseListByCategory(
            mainCurriculum.getCurriculumCodesJson(), Category.전공필수);

        ProcessorResponse.MajorRequiredDTO majorRequiredDTO = executeProcessor(
            ProcessorCategory.전공필수,
            majorRequiredCourseList,
            ProcessorRequest.MajorRequiredDTO.class,
            mainCurriculum.getAlternativeCoursesJson().getAlternativeCourseMap(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("majorRequiredDTO = " + majorRequiredDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        /*
         * 주전공-전공선택 실행
         */

        ProcessorResponse.MajorSelectDTO majorSelectDTO = executeProcessor(
            ProcessorCategory.전공선택,
            mainCurriculum.getCurriculumCodesJson().findRequiredCodesByCategory(Category.전공선택),
            ProcessorRequest.MajorSelectDTO.class,
            mainCurriculum.getAlternativeCoursesJson().getAlternativeCourseMap(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );

        // 전공선택 확인하면 끝

    }

    @SuppressWarnings("unchecked")
    private <R, T, P> R executeProcessor(
        ProcessorCategory category,
        T data,
        Class<P> payloadClazz,
        Map<String, Set<String>> alternativeCourseMap,
        DepartmentName departmentName,
        Integer joinYear,
        LinkedList<Take> allTakeList
    ) {
        try {
            P payload = payloadClazz.getConstructor(
                    data.getClass(),
                    Map.class,
                    DepartmentName.class,
                    Integer.class
                )
                .newInstance(data, alternativeCourseMap, departmentName, joinYear);
            return (R) ProcessorUtils.get(processorMap, category)
                .execute(payload, allTakeList);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.PROCESSOR_FIND_EXCEPTION, e);
        }
    }

    private List<Course> getEssentialCourseListByCategory(
        CurriculumCodesJson curriculumCodesJson,
        Category category
    ) {
        Set<String> essentialCodeSet = curriculumCodesJson.findRequiredCodesByCategory(category);
        return courseService.getCourseListByCodeSet(essentialCodeSet);
    }

}
