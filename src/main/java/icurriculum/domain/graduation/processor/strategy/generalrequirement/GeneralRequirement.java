package icurriculum.domain.graduation.processor.strategy.generalrequirement;

import icurriculum.domain.course.Course;
import icurriculum.domain.department.DepartmentName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GeneralRequirement {

    /**
     * 전공별 교양필수 과목 리스트
     * 학과 enum
     */

    private List<Course> generalRequirementCourses;
    private DepartmentName departmentName;

}
