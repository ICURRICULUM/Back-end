package icurriculum.domain.graduation.processor;

import icurriculum.domain.course.Course;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.GeneralRequirementDto;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.GeneralRequirement;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy.GeneralRequirementStrategy;
import icurriculum.domain.take.Take;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 학과별 교양필수
 * ex. 프로네시스 세미나, 일반수학1 ...
 * 제외 -> 핵심교양, SW-AI, 창의
 */

@RequiredArgsConstructor
public class GeneralRequirementProcessor implements Processor<GeneralRequirement, GeneralRequirementDto> {

    private final Map<DepartmentName, GeneralRequirementStrategy> generalRequirementStrategyMap;

    @Override
    public GeneralRequirementDto execute(GeneralRequirement requirement, List<Take> takes) {
        List<Course> generalRequirementCourses = requirement.getGeneralRequirementCourses();
        DepartmentName departmentName = requirement.getDepartmentName();

        return generalRequirementStrategyMap.get(departmentName)
                .execute(generalRequirementCourses, takes);
    }
}
