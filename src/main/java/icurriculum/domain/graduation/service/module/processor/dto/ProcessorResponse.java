package icurriculum.domain.graduation.service.module.processor.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CreditWithData;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.util.CourseUtils;
import icurriculum.global.util.GraduationUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ProcessorResponse {

    @Getter
    @ToString
    public static class SwAiDTO {

        private int completedCredit; // swAi 이수학점
        private int requiredCredit; // swAi 필요학점
        private boolean isClear; // 조건 충족 여부

        public void update(Take take, Iterator<Take> iterator, boolean isAreaAlternative) {
            completedCredit += take.getEffectiveCourse().getCredit();

            if (!isAreaAlternative) {
                iterator.remove();
            }
        }

        public void setRequiredCredit(SwAi swAi) {
            requiredCredit = swAi.getRequiredCredit();
        }

        public void checkIsClear() {
            isClear = completedCredit >= requiredCredit;
        }
    }

    @Getter
    @ToString
    public static class CreativityDTO {

        private int completedCredit; // 창의 이수학점
        private int requiredCredit; // 창의 필요학점
        private boolean isClear; // 조건 충족 여부

        public void update(Take take, Iterator<Take> iterator) {
            completedCredit += take.getEffectiveCourse().getCredit();
            iterator.remove();
        }

        public void setRequiredCredit(Creativity creativity) {
            requiredCredit = creativity.getRequiredCredit();
        }

        public void checkIsClear() {
            isClear = completedCredit >= requiredCredit;
        }
    }

    @Getter
    @ToString
    public static class CoreDTO {

        private int completedCredit; // 핵심교양 이수학점
        private int requiredCredit; // 핵심교양 필요학점
        private final Set<Category> uncompletedArea = new HashSet<>(); // 미이수 영역
        private boolean isClear; // 조건 충족 여부

        public void initUncompletedArea(Core core) {
            uncompletedArea.addAll(core.getRequiredAreaSet());
        }

        public void update(
            Take take,
            Iterator<Take> iterator,
            Category area,
            boolean isAreaAlternative
        ) {
            completedCredit += take.getEffectiveCourse().getCredit();
            uncompletedArea.remove(area);

            if (!isAreaAlternative) {
                iterator.remove();
            }
        }

        public void setRequiredCredit(Core core) {
            this.requiredCredit = core.getRequiredCredit();
        }

        public void checkIsClear(Core core) {
            if (!core.getIsAreaFixed()) {
                isClear = completedCredit >= requiredCredit;
                return;
            }
            isClear = uncompletedArea.isEmpty();
        }
    }

    @ToString
    public static class GeneralRequiredDTO {

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

        public void initGeneralRequiredResponse(List<Course> generalRequiredCourseList) {
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


    @ToString
    public static class MajorRequiredDTO {

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

        public void initMajorRequiredResponse(List<Course> majorRequiredCourseList) {
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

    @Getter
    @ToString
    public static class MajorSelectDTO {

        private int totalMajorCompletedCredit; // 전공(전공선택 + 전공필수) 이수학점
        private int totalMajorRequiredCredit; // 전공(전공선택 + 전공필수) 필요학점
        private boolean isClear; // 조건 충족 여부

        public void initMajorSelectResponse(CreditWithData creditWithData) {
            totalMajorCompletedCredit += creditWithData.majorRequiredCompletedCredit();
            totalMajorRequiredCredit += creditWithData.majorNeedCredit();
        }

        public void update(Take take, Iterator<Take> iterator) {
            totalMajorCompletedCredit += take.getEffectiveCourse().getCredit();
            iterator.remove();
        }

        public void checkIsClear() {
            isClear = totalMajorCompletedCredit >= totalMajorRequiredCredit;
        }
    }


}
