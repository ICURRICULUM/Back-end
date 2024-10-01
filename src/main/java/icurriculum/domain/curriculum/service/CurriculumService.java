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

    /*
     * Mongo DB에서 값을 가져올 때, 저장된 데이터가 없을 시 null 일 수 있음.
     * NullpointException 방지를 위해 validate 실행
     */
    public Curriculum getCurriculumByMemberMajor(MemberMajor memberMajor) {
        CurriculumDecider decider = convertToDecider(memberMajor);

        Curriculum curriculum = repository.findByDecider(decider)
            .orElseThrow(() -> new GeneralException(ErrorStatus.CURRICULUM_NOT_FOUND));
        curriculum.validate();

        return curriculum;
    }

    private CurriculumDecider convertToDecider(MemberMajor memberMajor) {
        return CurriculumDecider.builder().majorType(memberMajor.getMajorType())
            .departmentName(memberMajor.getDepartment().getName())
            .joinYear(memberMajor.getMember().getJoinYear()).build();
    }

}
