package icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.GeneralRequirementDto;
import icurriculum.domain.take.Take;

import java.util.List;

public interface GeneralRequirementStrategy {
    GeneralRequirementDto execute(List<Course> generalRequirementCourses, List<Take> takes);

}
