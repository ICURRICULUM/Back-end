package icurriculum.domain.graduation.service.module.processor.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.department.DepartmentName;
import java.util.List;

public abstract class ProcessorRequest {

    public record SwAiDTO(
        SwAi swAi,
        AlternativeCourse alternativeCourse,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record CoreDTO(
        Core core,
        AlternativeCourse alternativeCourse,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record CreativityDTO(
        Creativity creativity,
        AlternativeCourse alternativeCourse,
        DepartmentName departmentName,
        Integer joinYear) {

    }

    public record GeneralRequiredDTO(
        CourseListWithData<GeneralRequired> CourseListWithData,
        AlternativeCourse alternativeCourse,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record MajorRequiredDTO(
        CourseListWithData<MajorRequired> CourseListWithData,
        AlternativeCourse alternativeCourse,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    public record MajorSelectDTO(
        CreditWithData creditWithData,
        AlternativeCourse alternativeCourse,
        DepartmentName departmentName,
        Integer joinYear
    ) {

    }

    /*
     * 전공필수, 교양필수 필요 데이터
     *
     * - 들어야하는 CourseList
     * - data(MajorRequired or GeneralRequired): Set<String> 형태의 커리큘럼 과목 코드(크롤러), 추가 정보(임의로 자유롭게 가능)
     */
    public record CourseListWithData<T>(
        List<Course> essentialCourseList,
        T data
    ) {

    }

    /*
     * 전공선택 필요 데이터
     *
     * - MajorSelect: Set<String> 형태의 커리큘럼 과목 코드(크롤러), 추가 정보(임의로 자유롭게 가능)
     * - 전공 필요학점
     * - 전공필수 계산된 이수학점 -> 전공필요학점 충족 확인에 필요
     */
    public record CreditWithData(
        MajorSelect majorSelect,
        int majorNeedCredit,
        int majorRequiredCompletedCredit
    ) {

    }


}
