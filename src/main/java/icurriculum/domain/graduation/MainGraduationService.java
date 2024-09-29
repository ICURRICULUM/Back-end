package icurriculum.domain.graduation;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.config.ProcessorCategory;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest.CourseListWithData;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest.CreditData;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest.CreditWithData;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.service.MemberMajorService;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.service.TakeService;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.DTOUtils;
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

        // 주전공 Take LinkedList 가져온다.
        LinkedList<Take> allTakeList = takeService
            .getTakeListByMemberAndMajorType(member, MajorType.주전공);

        // 주전공 가져온다.
        MemberMajor mainMemberMajor = memberMajorService
            .getMemberMajorByMemberAndMajorType(member, MajorType.주전공);

        // 주전공 졸업요건(Curriculum)을 가져온다.
        Curriculum mainCurriculum = curriculumService.getCurriculumByMemberMajor(mainMemberMajor);
        System.out.println("mainCurriculum = " + mainCurriculum);

        // SW_AI
        ProcessorResponse.SwAiDTO swAiDTO = executeProcessor(
            ProcessorCategory.SW_AI,
            ProcessorRequest.SwAiDTO.class,
            mainCurriculum.getSwAi(),
            mainCurriculum.getAlternativeCourse(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("swAiDTO = " + swAiDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        // 창의
        ProcessorResponse.CreativityDTO creativityDTO = executeProcessor(
            ProcessorCategory.창의,
            ProcessorRequest.CreativityDTO.class,
            mainCurriculum.getCreativity(),
            mainCurriculum.getAlternativeCourse(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("creativityDTO = " + creativityDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        // 핵심교양
        ProcessorResponse.CoreDTO coreDTO = executeProcessor(
            ProcessorCategory.핵심교양,
            ProcessorRequest.CoreDTO.class,
            mainCurriculum.getCore(),
            mainCurriculum.getAlternativeCourse(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("coreDTO = " + coreDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        // 주전공-전공필수
        CourseListWithData<MajorRequired> majorRequiredData = createMajorRequiredData(
            mainCurriculum.getMajorRequired()
        );

        ProcessorResponse.MajorRequiredDTO majorRequiredDTO = executeProcessor(
            ProcessorCategory.전공필수,
            ProcessorRequest.MajorRequiredDTO.class,
            majorRequiredData,
            mainCurriculum.getAlternativeCourse(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("majorRequiredDTO = " + majorRequiredDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        // 주전공-전공선택, 이수한 전공학점 계산
        CreditWithData majorSelectData = new CreditWithData(
            mainCurriculum.getMajorSelect(),
            mainCurriculum.getRequiredCredit().getSingleNeedCredit(),
            majorRequiredDTO.getCompletedCredit()
        );

        ProcessorResponse.MajorSelectDTO majorSelectDTO = executeProcessor(
            ProcessorCategory.전공선택,
            ProcessorRequest.MajorSelectDTO.class,
            majorSelectData,
            mainCurriculum.getAlternativeCourse(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("majorSelectDTO = " + majorSelectDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        // 주전공-교양필수
        CourseListWithData<GeneralRequired> generalRequiredData = createGeneralRequiredData(
            mainCurriculum.getGeneralRequired()
        );
        ProcessorResponse.GeneralRequiredDTO generalRequiredDTO = executeProcessor(
            ProcessorCategory.교양필수,
            ProcessorRequest.GeneralRequiredDTO.class,
            generalRequiredData,
            mainCurriculum.getAlternativeCourse(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );
        System.out.println("generalRequiredDTO = " + generalRequiredDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());

        // 교양선택, 이수한 총학점 계산
        CreditData generalSelectData = createCreditData(
            mainCurriculum.getRequiredCredit(),
            creativityDTO,
            swAiDTO,
            coreDTO,
            majorSelectDTO,
            generalRequiredDTO
        );

        ProcessorResponse.GeneralSelectDTO generalSelectDTO = executeProcessor(
            ProcessorCategory.교양선택,
            ProcessorRequest.GeneralSelectDTO.class,
            generalSelectData,
            mainCurriculum.getAlternativeCourse(),
            mainMemberMajor.getDepartment().getName(),
            member.getJoinYear(),
            allTakeList
        );

        for (Take take : allTakeList) {
            System.out.println("take = " + take);
        }
        System.out.println("generalSelectDTO = " + generalSelectDTO);
        System.out.println("allTakeList.size() = " + allTakeList.size());
    }

    @SuppressWarnings("unchecked")
    private <R, T, P> R executeProcessor(
        ProcessorCategory category,
        Class<P> payloadClazz,
        T data,
        AlternativeCourse alternativeCourse,
        DepartmentName departmentName,
        Integer joinYear,
        LinkedList<Take> allTakeList
    ) {
        try {
            P payload = payloadClazz.getConstructor(
                    data.getClass(),
                    AlternativeCourse.class,
                    DepartmentName.class,
                    Integer.class
                )
                .newInstance(data, alternativeCourse, departmentName, joinYear);

            return (R) ProcessorUtils.get(processorMap, category)
                .execute(payload, allTakeList);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.PROCESSOR_DATA_EXCEPTION, e);
        }
    }

    private CourseListWithData<MajorRequired> createMajorRequiredData(
        MajorRequired majorRequired
    ) {
        Set<String> majorRequiredCodeSet = majorRequired.getCodeSet();
        List<Course> courseList = courseService.getCourseListByCodeSet(majorRequiredCodeSet);

        return new CourseListWithData<>(courseList, majorRequired);
    }

    private CourseListWithData<GeneralRequired> createGeneralRequiredData(
        GeneralRequired generalRequired
    ) {
        Set<String> generalRequiredCodeSet = generalRequired.getCodeSet();
        List<Course> courseList = courseService.getCourseListByCodeSet(generalRequiredCodeSet);
        return new CourseListWithData<>(courseList, generalRequired);
    }

    private CreditData createCreditData(
        RequiredCredit requiredCredit,
        ProcessorResponse.CreativityDTO creativityDTO,
        ProcessorResponse.SwAiDTO swAiDTO,
        ProcessorResponse.CoreDTO coreDTO,
        ProcessorResponse.MajorSelectDTO majorSelectDTO,
        ProcessorResponse.GeneralRequiredDTO generalRequiredDTO
    ) {
        final int completedCreditExceptGeneralSelect = DTOUtils.calculateCompletedCreditExceptGeneralSelect(
            creativityDTO,
            swAiDTO,
            coreDTO,
            majorSelectDTO,
            generalRequiredDTO
        );

        return new CreditData(
            requiredCredit.getTotalNeedCredit(),
            completedCreditExceptGeneralSelect
        );
    }

}
