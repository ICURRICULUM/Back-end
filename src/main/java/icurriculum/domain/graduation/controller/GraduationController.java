package icurriculum.domain.graduation.controller;

import icurriculum.domain.graduation.dto.GraduationResponse;
import icurriculum.domain.graduation.service.AllGraduationService;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GraduationController {

    private final AllGraduationService allGraduationService;
    private final MemberRepository testMemberRepository; // 삭제 예정

    @PostMapping("check-graduation")
    public ApiResponse<GraduationResponse.AllDTO> checkAll(
        Member member
    ) {

        /**
         * Todo
         * 1. Argument 내에 회원 데이터가 들어와야 함.
         * 2. 현재는 단일전공 기능만 제공, 추후 다른 전공상태도 제공
         */

        Member testMember = testMemberRepository.findById(1L).get(); // 삭제 예정
        return ApiResponse.onSuccess(
            allGraduationService.executeAll(testMember)
        );
    }

}
