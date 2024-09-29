package icurriculum.domain.graduation.processor.majorrequired.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest.CourseListWithData;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
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
        ProcessorResponse.MajorRequiredDTO response = new ProcessorResponse.MajorRequiredDTO();
        response.initMajorRequiredResponse(
            request.CourseListWithData().essentialCourseList()
        );

        handleResponse(allTakeList, request.CourseListWithData(),
            request.alternativeCourse(), response);

        return response;
    }

    private void handleResponse(
        LinkedList<Take> allTakeList,
        CourseListWithData<MajorRequired> courseListWithData,
        AlternativeCourse alternativeCourse,
        ProcessorResponse.MajorRequiredDTO response
    ) {

        List<Course> majorRequiredCourseList = courseListWithData.essentialCourseList();
        Set<String> majorRequiredCodeSet = courseListWithData.data().getCodeSet();

        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (GraduationUtils.isApproved(take, majorRequiredCodeSet)) {
                response.update(take, iterator);
                continue;
            }

            if (GraduationUtils.isCodeAlternative(take, majorRequiredCodeSet, alternativeCourse)) {
                response.update(take, iterator);
            }
        }

        response.setRequiredCredit(majorRequiredCourseList);
        response.checkIsClear();
    }

}




