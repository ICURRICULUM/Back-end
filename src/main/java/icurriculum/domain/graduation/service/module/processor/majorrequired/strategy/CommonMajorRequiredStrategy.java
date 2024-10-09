package icurriculum.domain.graduation.service.module.processor.majorrequired.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorConverter;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CourseListWithData;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorResponse;
import icurriculum.domain.graduation.service.module.processor.majorrequired.MajorRequiredResult;
import icurriculum.domain.take.Take;
import icurriculum.global.util.GraduationUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonMajorRequiredStrategy implements MajorRequiredStrategy {

    @Override
    public ProcessorResponse.MajorRequiredDTO execute(
        ProcessorRequest.MajorRequiredDTO request,
        LinkedList<Take> allTakeList
    ) {
        MajorRequiredResult result = new MajorRequiredResult();
        result.initMajorRequiredResult(
            request.CourseListWithData().essentialCourseList()
        );

        handleResult(
            allTakeList,
            request.CourseListWithData(),
            request.alternativeCourse(),
            result
        );

        return ProcessorConverter.to(result);
    }

    private void handleResult(
        LinkedList<Take> allTakeList,
        CourseListWithData<MajorRequired> courseListWithData,
        AlternativeCourse alternativeCourse,
        MajorRequiredResult result
    ) {

        List<Course> majorRequiredCourseList = courseListWithData.essentialCourseList();
        Set<String> majorRequiredCodeSet = courseListWithData.data().getCodeSet();

        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (GraduationUtils.isApproved(take, majorRequiredCodeSet)) {
                result.update(take, take.getEffectiveCourse().getCode(), iterator);
                continue;
            }
            GraduationUtils.getAlternativeCode(take, majorRequiredCodeSet, alternativeCourse)
                .ifPresent(
                    alternativeCode -> result.update(take, alternativeCode, iterator)
                );
        }

        result.setRequiredCredit(majorRequiredCourseList);
        result.setUncompletedCourseList();
        result.checkIsClear();
    }

}




