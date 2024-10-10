package icurriculum.domain.graduation.service.module.processor.majorrequired;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Take;
import icurriculum.global.util.CourseUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@ToString
public class MajorRequiredResult {

    /*
     * 응답값
     */
    @Getter
    private int completedCredit; // 전공필수 이수학점
    @Getter
    private int requiredCredit; // 전공필수 필요학점
    @Getter
    private final List<Course> uncompletedCourseList = new ArrayList<>(); // 미이수 과목
    @Getter
    private boolean isClear; // 조건 충족 여부

    /*
     * 임시 데이터
     */
    private final Map<String, Course> majorRequiredCourseMap = new HashMap<>(); // 교양필수 Course Map

    public void initMajorRequiredResult(List<Course> majorRequiredCourseList) {
        for (Course course : majorRequiredCourseList) {
            majorRequiredCourseMap.put(course.getCode(), course);
        }
    }

    public void update(Take take, String code, Iterator<Take> iterator) {
        completedCredit += take.getEffectiveCourse().getCredit();
        majorRequiredCourseMap.remove(code);
        iterator.remove();
    }

    public void setUncompletedCourseList() {
        uncompletedCourseList.addAll(majorRequiredCourseMap.values());
    }

    public void setRequiredCredit(List<Course> generalRequiredCourseList) {
        requiredCredit = CourseUtils.calculateTotalCredit(generalRequiredCourseList);
    }

    public void checkIsClear() {
        isClear = uncompletedCourseList.isEmpty();
    }

}