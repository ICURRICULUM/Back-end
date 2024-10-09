package icurriculum.domain.graduation.service.module.processor.generalrequired.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorConverter;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CourseListWithData;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.service.module.processor.generalrequired.GeneralRequiredResult;
import icurriculum.domain.take.Take;
import icurriculum.global.util.GraduationUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonGeneralRequiredStrategy implements GeneralRequiredStrategy {

    /*
     * [logic]
     * 1. 초기화
     *      필수로 들어야하는 Course들을 uncompletedCourseSet에 추가.
     *          이수한 것으로 확인되는 Course는 uncompletedCourseSet에서 하나씩 삭제.
     * 2. take loop
     *      - generalRequiredCodeSet에 포함되면 response 계산 수행
     *      - take의 대체과목 중 generalRequiredCodeSet에 포함되어 과목대체가 가능하면 response 계산 수행
     * 3. joinYear에 따라서 '필요학점 및 조건 달성 여부'를 계산
     *      이유: 영어과목으로 인한 분기
     */
    @Override
    public ProcessorResponse.GeneralRequiredDTO execute(
        ProcessorRequest.GeneralRequiredDTO request,
        LinkedList<Take> allTakeList
    ) {
        GeneralRequiredResult result = new GeneralRequiredResult();
        result.initGeneralRequiredResult(
            request.CourseListWithData().essentialCourseList()
        );

        handleResult(
            allTakeList,
            request.CourseListWithData(),
            request.alternativeCourse(),
            request.joinYear(),
            result
        );

        return ProcessorConverter.to(result);
    }

    private void handleResult(
        LinkedList<Take> allTakeList,
        CourseListWithData<GeneralRequired> CourseListWithData,
        AlternativeCourse alternativeCourse,
        final int joinYear,
        GeneralRequiredResult result
    ) {
        List<Course> generalRequiredCourseList = CourseListWithData.essentialCourseList();
        Set<String> generalRequiredCodeSet = CourseListWithData.data().getCodeSet();

        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (GraduationUtils.isApproved(take, generalRequiredCodeSet)) {
                result.update(take, take.getEffectiveCourse().getCode(), iterator, joinYear);
                continue;
            }

            GraduationUtils.getAlternativeCode(take, generalRequiredCodeSet, alternativeCourse)
                .ifPresent(
                    alternativeCode -> result.update(take, alternativeCode, iterator, joinYear)
                );

        }

        result.setRequiredCredit(generalRequiredCourseList, joinYear);
        result.setUncompletedCourseList();
        result.checkIsClear(joinYear);
    }


}




