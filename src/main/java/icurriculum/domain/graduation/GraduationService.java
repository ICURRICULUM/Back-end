package icurriculum.domain.graduation;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.json.AlternativeCourseJson;
import icurriculum.domain.curriculum.json.CurriculumCodesJson;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.ProcessorCategory;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.*;
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

import static icurriculum.domain.curriculum.util.MajorToDeciderConverter.toDecider;
import static icurriculum.domain.curriculum.util.MajorToDeciderConverter.toDeciders;
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
         * 수강 List 가져온다.
         */
        List<Take> takes = takeService.findTakesByMember(member);

        /**
         * 회원 전공 상태 List 가져온다.
         */
        List<MemberMajor> memberMajors = memberMajorService.findMajorsByMember(member);

        /**
         * 주전공 졸업요건(Curriculum)을 가져온다.
         */
        Curriculum mainCurriculum = findMainCurriculum(memberMajors);

        /**
         * 핵심교양 실행
         */
/*        CoreDto coreDto = (CoreDto) getProcessorByCategory(processorMap, ProcessorCategory.핵심교양)
                .execute(mainCurriculum.getCoreJson(), getCoreTakes(takes));*/
        CoreDto coreDto = (CoreDto) getProcessorByCategory(processorMap, ProcessorCategory.핵심교양)
                .execute(mainCurriculum.getCoreJson(), takes);

        /**
         * SW_AI 실행
         */
/*        SwAiDto swAiDto = (SwAiDto) getProcessorByCategory(processorMap, ProcessorCategory.SW_AI)
                .execute(mainCurriculum.getSwAiJson(), getTakesByCategory(takes, Category.SW_AI));*/
        SwAiDto swAiDto = (SwAiDto) getProcessorByCategory(processorMap, ProcessorCategory.SW_AI)
                .execute(mainCurriculum.getSwAiJson(), takes);

        /**
         * 창의 실행
         */
/*        CreativeDto creativeDto = (CreativeDto) getProcessorByCategory(processorMap, ProcessorCategory.창의)
                .execute(mainCurriculum.getCreativityJson(), getTakesByCategory(takes, Category.창의));*/
        CreativeDto creativeDto = (CreativeDto) getProcessorByCategory(processorMap, ProcessorCategory.창의)
                .execute(mainCurriculum.getCreativityJson(), takes);


        /**
         * 주전공-교양필수 실행
         */
        GeneralRequirementDto generalRequirementDto = executeGeneralRequirement(
                mainCurriculum.getCurriculumCodesJson().findCodesByCategory(Category.교양필수),
                mainCurriculum.getAlternativeCourseJson(),
                mainCurriculum.getDecider().department().getName(),
                member.getJoinYear(),
                takes
        );


        // 6. 전공 프로세스 진행
        전공_프로세스_진행();
    }

    private Curriculum findMainCurriculum(List<MemberMajor> memberMajors) {
        MemberMajor mainMajor = findMainMajor(memberMajors);
        CurriculumDecider mainDecider = toDecider(mainMajor);
        return curriculumService.findCurriculumByDeciderOnlyMain(mainDecider);
    }

    private List<Curriculum> findOtherCurriculums(List<MemberMajor> memberMajors) {
        List<MemberMajor> otherMajors = findOtherMajors(memberMajors);
        List<CurriculumDecider> otherDeciders = toDeciders(otherMajors);
        return curriculumService.findCurriculumsByDecidersOnlyOthers(otherDeciders);
    }

    private GeneralRequirementDto executeGeneralRequirement(
            Set<String> generalRequirementCodes,
            AlternativeCourseJson alternativeCourseJson,
            DepartmentName departmentName,
            Integer joinYear,
            List<Take> takes
    ) {
        List<Course> generalRequirementCourses = courseService.findCoursesByCodes(generalRequirementCodes);
        GeneralRequirement generalRequirement = new GeneralRequirement(
                generalRequirementCourses,
                alternativeCourseJson,
                departmentName,
                joinYear);

        return (GeneralRequirementDto) getProcessorByCategory(processorMap, ProcessorCategory.교양필수)
                .execute(generalRequirement, getTakesByCategory(takes, Category.교양필수));
    }

    private void 전공_프로세스_진행() {

    }


}
