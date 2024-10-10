package icurriculum.domain.course.controller;

import icurriculum.domain.course.dto.CourseRequest;
import icurriculum.domain.course.dto.CourseResponse.DetailInfoDTO;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.service.MemberMajorService;
import icurriculum.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final MemberMajorService memberMajorService;

    @GetMapping("/single")
    public ApiResponse<DetailInfoDTO> getCourse(
            Member member,
            @RequestBody CourseRequest.SimpleInfoDTO request) {
        MajorType majorType = MajorType.valueOf(request.getMajorType());
        MemberMajor memberMajor = memberMajorService.getMemberMajorByMemberAndMajorType(member,majorType);
        DetailInfoDTO course = courseService.getCourse(request.getCode(), memberMajor);

        return ApiResponse.onSuccess(course);
    }
}
