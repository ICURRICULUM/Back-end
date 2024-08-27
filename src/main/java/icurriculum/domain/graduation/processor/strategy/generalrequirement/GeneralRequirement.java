package icurriculum.domain.graduation.processor.strategy.generalrequirement;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.json.AlternativeCourseJson;
import icurriculum.domain.department.DepartmentName;

import java.util.List;

/**
 * 전공별 교양필수 과목 리스트
 * 대체과목
 * 학과 enum
 * 입학년도
 */

public record GeneralRequirement(List<Course> generalRequirementCourses,
                                 AlternativeCourseJson alternativeCourses,
                                 DepartmentName departmentName,
                                 Integer joinYear) {

}
