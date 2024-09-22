package icurriculum.domain.membermajor.service;

import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.MemberMajorUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberMajorService {

    private final MemberMajorRepository repository;

    public List<MemberMajor> getMemberMajorListByMember(Member member) {
        List<MemberMajor> memberMajorList = repository.findByMember(member);
        checkValidMemberMajorList(memberMajorList);
        return memberMajorList;
    }

    public MemberMajor getMemberMajorByMemberAndMajorType(
        Member member,
        MajorType majorType
    ) {
        return repository.findByMemberAndMajorType(member, majorType)
            .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_MAJOR_NOT_FOUND));
    }


    /**
     * 전체 전공 List validation method
     * <p>
     * 1. 회원의 전공은 한 개 이상 이어야 한다.
     * <p>
     * 2. 회원의 전공상태 중 주전공이 하나라도 있어야 한다.
     */
    private void checkValidMemberMajorList(List<MemberMajor> memberMajorList) {
        if (memberMajorList.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_MAJOR_NOT_FOUND);
        }
        MemberMajorUtils.extractMainMemberMajor(memberMajorList);
    }

}
