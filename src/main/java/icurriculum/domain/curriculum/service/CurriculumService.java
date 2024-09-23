package icurriculum.domain.curriculum.service;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository repository;

    public Curriculum getCurriculumByMemberMajor(MemberMajor memberMajor) {
        CurriculumDecider decider = convertToDecider(memberMajor);
        return repository.findByDecider(decider)
            .orElseThrow(() -> new GeneralException(ErrorStatus.CURRICULUM_NOT_FOUND));
    }

    private CurriculumDecider convertToDecider(MemberMajor memberMajor) {
        return new CurriculumDecider(
            memberMajor.getMajorType(),
            memberMajor.getDepartment().getName(),
            memberMajor.getMember().getJoinYear()
        );
    }

}
