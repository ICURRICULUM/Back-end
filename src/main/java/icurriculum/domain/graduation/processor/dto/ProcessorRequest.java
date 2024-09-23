package icurriculum.domain.graduation.processor.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.curriculum.json.CreativityJson;
import icurriculum.domain.curriculum.json.CurriculumCodeJson;
import icurriculum.domain.curriculum.json.RequiredCreditJson;
import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.domain.department.DepartmentName;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ProcessorRequest {

    public record SwAiDTO(
        SwAiJson swAiJson,
        Map<String, Set<String>> alternativeCourseCodeMap,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record CoreDTO(
        CoreJson coreJson,
        Map<String, Set<String>> alternativeCourseCodeMap,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record CreativityDTO(
        CreativityJson creativityJson,
        Map<String, Set<String>> alternativeCourseCodeMap,
        DepartmentName departmentName,
        Integer joinYear) {

    }

    public record GeneralRequiredDTO(
        CourseListWithCurriculumData courseListWithCurriculumData,
        Map<String, Set<String>> alternativeCourseCodeMap,
        DepartmentName departmentName,
        Integer joinYear
    ) {

        private static final int CURRICULUM_CHANGED_YEAR = 21;

        /*
         * 영어 기초
         */
        public static final Course 의사소통_영어 = Course.builder()
            .name("의사소통 영어")
            .code("GEB1107")
            .credit(3)
            .build();
        public static final Course 의사소통_영어_중급 = Course.builder()
            .name("의사소통 영어: 중급")
            .code("GEB1108")
            .credit(3)
            .build();
        public static final Course 의사소통_영어_고급 = Course.builder()
            .name("의사소통 영어: 고급")
            .code("GEB1109")
            .credit(3)
            .build();

        /*
         * 영어 심화 : 옛날 학수번호이다.
         * - Curriculum 에 옛날 학수번호로 저장 되어있음.
         */
        public static final Course 실용영어_LS = Course.builder()
            .name("실용영어 L/S")
            .code("GEB1201")
            .credit(3)
            .build();
        public static final Course 실용영어_RW = Course.builder()
            .name("실용영어 R/W")
            .code("GEB1202")
            .credit(3)
            .build();
        public static final Course 고급대학영어 = Course.builder()
            .name("고급대학영어")
            .code("GEB1203")
            .credit(3)
            .build();

        public static boolean isCurriculumChanged(final int joinYear) {
            return joinYear >= CURRICULUM_CHANGED_YEAR;
        }
    }

    public record MajorRequiredDTO(
        CourseListWithCurriculumData courseListWithCurriculumData,
        Map<String, Set<String>> alternativeCourseCodeMap,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record MajorSelectDTO(
        CurriculumWithCredit curriculumWithCredit,
        Map<String, Set<String>> alternativeCourseCodeMap,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record GeneralSelectDTO(
        CreditData creditData,
        Map<String, Set<String>> alternativeCourseCodeMap,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    /*
     * 전공필수, 교양필수 필요 데이터
     * - 들어야하는 CourseList
     * - CurriculumCodeJson(추가 정보 여기에 저장)
     */
    public record CourseListWithCurriculumData(
        List<Course> CourseList,
        CurriculumCodeJson curriculumCodeJsonForAdditionalData
    ) {

    }

    /* 전공선택 필요 데이터
     * - CurriculumCodeJson(추가 정보 여기에 저장)
     * - 필요학점 데이터
     * - 전공필수 계산된 이수학점 -> 전공필요학점 충족 확인에 필요
     */
    public record CurriculumWithCredit(
        CurriculumCodeJson curriculumCodeJson,
        RequiredCreditJson requiredCreditJson,
        Integer majorRequiredCompletedCredit
    ) {

    }

    /* 교양선택 필요 데이터
     * - 필요학점 데이터
     * - 교양선택 제외하고 계산된 이수학점 -> 총필요학점 충족 확인에 필요
     */
    public record CreditData(
        RequiredCreditJson requiredCreditJson,
        Integer completedCreditExceptGeneralSelect
    ) {

    }


}
