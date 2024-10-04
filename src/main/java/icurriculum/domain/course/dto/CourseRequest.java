package icurriculum.domain.course.dto;

import icurriculum.domain.membermajor.MemberMajor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CourseRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleInfoDTO {
        String code;
        String majorType;
    }
}
