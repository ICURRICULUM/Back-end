package icurriculum.domain.graduation.service.module;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.dto.GraduationConverter;
import icurriculum.domain.graduation.dto.GraduationResponse;
import icurriculum.domain.graduation.service.module.processor.Processor;
import icurriculum.domain.graduation.service.module.processor.config.ProcessorCategory;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CourseListWithData;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CreditWithData;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
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
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/*
 * 주전공 졸업요건 확인 클래스
 */

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainGraduationService {

    private final TakeService takeService;
    private final MemberMajorService memberMajorService;
    private final CurriculumService curriculumService;
    private final CourseService courseService;

    private final Map<ProcessorCategory, Processor<?, ?>> processorMap;

    public GraduationResponse.MainDTO execute(Member member) {

        // 주전공 Take LinkedList 가져온다.
        LinkedList<Take> allTakeList = takeService
            .getTakeListByMemberAndMajorType(member, MajorType.주전공);

        // 주전공 가져온다.
        MemberMajor mainMemberMajor = memberMajorService
            .getMemberMajorByMemberAndMajorType(member, MajorType.주전공);

        // 주전공 졸업요건(Curriculum)을 가져온다.
        Curriculum mainCurriculum = curriculumService.getCurriculumByMemberMajor(mainMemberMajor);

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

        // 주전공-전공선택, 이수한 전공학점 계산
        CreditWithData majorSelectData = new CreditWithData(
            mainCurriculum.getMajorSelect(),
            mainCurriculum.getRequiredCredit().getSingleNeedCredit(),
            majorRequiredDTO.completedCredit()
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

        // 교양선택 - 남은 수업 모두 교양선택인지 확인
        validateRemainTakeListGeneralSelect(allTakeList);
        final int totalNeedCredit = mainCurriculum.getRequiredCredit().getTotalNeedCredit();

        return GraduationConverter.toMainDTO(
            swAiDTO,
            creativityDTO,
            coreDTO,
            majorRequiredDTO,
            majorSelectDTO,
            generalRequiredDTO,
            totalNeedCredit
        );
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
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e
        ) {
            throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR, e);
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

    private void validateRemainTakeListGeneralSelect(LinkedList<Take> allTakeList) {
        for (Take take : allTakeList) {
            if (take.getCategory() != Category.교양선택) {
                log.error("남아있는 take 는 모두 교양선택이어야 합니다: {}", take);
                throw new GeneralException(ErrorStatus.CATEGORY_IS_NOT_VALID);
            }
        }
    }

}
