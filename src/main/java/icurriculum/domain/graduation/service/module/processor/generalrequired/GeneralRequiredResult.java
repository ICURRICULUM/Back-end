package icurriculum.domain.graduation.service.module.processor.generalrequired;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Take;
import icurriculum.global.util.CourseUtils;
import icurriculum.global.util.GraduationUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;

@ToString
public class GeneralRequiredResult {

    /*
     * 응답 값
     */
    @Getter
    private int completedCredit; // 교양필수 이수학점
    @Getter
    private int requiredCredit; // 교양필수 필요학점
    @Getter
    private final Set<Course> uncompletedCourseSet = new HashSet<>(); // 미이수 과목
    @Getter
    boolean isClear; // 조건 충족 여부

    /*
     * 임시 데이터
     */
    private boolean isClearEnglishBasic; // 영어 기초 이수 여부
    private boolean isClearEnglishAdvance; // 영어 심화 이수 여부
    private final Map<String, Course> generalRequiredCourseMap = new HashMap<>(); // 교양필수 Course Map

    public void initGeneralRequiredResult(List<Course> generalRequiredCourseList) {
        for (Course course : generalRequiredCourseList) {
            generalRequiredCourseMap.put(course.getCode(), course);
        }
    }

    public void update(Take take, String code, Iterator<Take> iterator, final int joinYear) {
        completedCredit += take.getEffectiveCourse().getCredit();
        generalRequiredCourseMap.remove(code);
        updateEnglishStatus(take.getEffectiveCourse(), joinYear);
        iterator.remove();
    }

    // 하드코딩, 추후 변경
    public void setRequiredCredit(List<Course> generalRequiredCourseList, final int joinYear) {
        int totalCredit = CourseUtils.calculateTotalCredit(generalRequiredCourseList);

        if (GraduationUtils.isCurriculumChanged(joinYear)) {
            requiredCredit = totalCredit - 6;
            return;
        }
        requiredCredit = totalCredit - 12;
    }

    public void setUncompletedCourseList() {
        uncompletedCourseSet.addAll(generalRequiredCourseMap.values());
    }

    public void checkIsClear(final int joinYear) {
        handleEnglishIfTakenEnglish(joinYear);
        isClear = uncompletedCourseSet.isEmpty();
    }

    private void updateEnglishStatus(Course takenCourse, final int joinYear) {
        if (GraduationUtils.isBasicEnglishCourse(takenCourse)) {
            isClearEnglishBasic = true;
        }

        if (GraduationUtils.isCurriculumChanged(joinYear)) {
            return;
        }

        if (GraduationUtils.isAdvanceEnglishCourse(takenCourse)) {
            isClearEnglishAdvance = true;
        }
    }

    /*
     * 영어 이수 확인 시 uncompletedCourseSet에서 영어 관련 과목 삭제
     *
     * - 입학년도에 따라 분기처리
     */
    private void handleEnglishIfTakenEnglish(final int joinYear) {
        if (isClearEnglishBasic) {
            GraduationUtils.removeBasicEnglishCourse(uncompletedCourseSet);
        }

        if (GraduationUtils.isCurriculumChanged(joinYear)) {
            return;
        }

        if (isClearEnglishAdvance) {
            GraduationUtils.removeAdvanceEnglishCourse(uncompletedCourseSet);
        }
    }
}
