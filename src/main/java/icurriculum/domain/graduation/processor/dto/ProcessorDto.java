package icurriculum.domain.graduation.processor.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.take.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

public class ProcessorDto {

    @Data
    @AllArgsConstructor
    public static class CreativeDto {

        /**
         * 이수학점, 기준학점
         * 조건충족 여부 boolean
         */

        private int completedCredit;
        private int requiredCredits;
        private boolean isClear;
    }

    @Data
    @AllArgsConstructor
    public static class SwAiDto {

        /**
         * 이수학점, 기준학점
         * 조건충족 여부 boolean
         */

        private int completedCredit;
        private int requiredCredits;
        private boolean isClear;
    }

    @Data
    @AllArgsConstructor
    public static class CoreDto {

        /**
         * 이수학점, 기준학점
         * 미이수 영역
         * 조건충족 여부 boolean
         */

        private int completedCredit;
        private int requiredCredits;
        private Set<Category> uncompletedArea;
        private boolean isClear;
    }

    @Data
    @AllArgsConstructor
    public static class GeneralRequirementDto {

        /**
         * 이수학점, 기준학점
         * 미이수 과목 코드
         * 조건충족 여부 boolean
         */

        private int completedCredit;
        private int requiredCredits;
        private List<Course> uncompletedCourses;
        private boolean isClear;

    }


}
