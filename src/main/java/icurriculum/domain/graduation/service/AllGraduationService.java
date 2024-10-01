package icurriculum.domain.graduation.service;

import icurriculum.domain.graduation.dto.GraduationConverter;
import icurriculum.domain.graduation.dto.GraduationResponse;
import icurriculum.domain.graduation.service.module.ConvergentGraduationService;
import icurriculum.domain.graduation.service.module.DoubleGraduationService;
import icurriculum.domain.graduation.service.module.InterGraduationService;
import icurriculum.domain.graduation.service.module.MainGraduationService;
import icurriculum.domain.graduation.service.module.MinorGraduationService;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.service.MemberMajorService;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.service.TakeService;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.TakeUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AllGraduationService {

    private final TakeService takeService;

    private final MemberMajorService memberMajorService;

    private final MainGraduationService mainGraduationService;

    /**
     * Todo
     * 구현 예정
     */
    private final DoubleGraduationService doubleGraduationService;
    private final ConvergentGraduationService convergentGraduationService;
    private final InterGraduationService interGraduationService;
    private final MinorGraduationService minorGraduationService;

    public GraduationResponse.AllDTO executeAll(Member member) {
        List<MemberMajor> memberMajorList = memberMajorService
            .getMemberMajorListByMember(member);

        List<Object> dtoList = memberMajorList.stream()
            .map(memberMajor -> execute(memberMajor, member))
            .collect(Collectors.toList());

        /**
         * Todo
         * 총 이수학점 계산
         */
        final int totalCompletedCredit = calculateTotalCompletedCredit(member);
        return GraduationConverter.toAllDTO(dtoList, totalCompletedCredit);
    }

    private int calculateTotalCompletedCredit(Member member){
        List<Take> alltakeList = takeService.getTakeListByMember(member);
        return TakeUtils.calculateTotalCredit(alltakeList);
    }

    @SuppressWarnings("unchecked")
    private <T> T execute(
        MemberMajor memberMajor,
        Member member
    ) {
        if (memberMajor.getMajorType() == MajorType.주전공) {
            return (T) mainGraduationService.execute(member);
        }
/*        if (memberMajor.getMajorType() == MajorType.복수전공) {
            return (T) doubleGraduationService.execute(member);
        }
        if (memberMajor.getMajorType() == MajorType.융합전공) {
            return (T) convergentGraduationService.execute(member);
        }
        if (memberMajor.getMajorType() == MajorType.연계전공) {
            return (T) interGraduationService.execute(member);
        }
        if (memberMajor.getMajorType() == MajorType.부전공) {
            return (T) minorGraduationService.execute(member);
        }*/
        throw new GeneralException(ErrorStatus.MAJOR_TYPE_NOT_FOUND, "지원 되지않는 전공상태입니다");
    }

}
