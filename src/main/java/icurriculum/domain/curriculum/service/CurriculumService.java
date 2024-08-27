package icurriculum.domain.curriculum.service;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.membermajor.MajorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository repository;

    /**
     * 주전공 일때만 사용하기
     */
    public Curriculum findCurriculumByDeciderOnlyMain(CurriculumDecider decider) {
        checkValidDecider(decider);
        return repository.findByDecider(decider)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * 주전공을 제외한 전공일 때만 사용하기
     */
    public List<Curriculum> findCurriculumsByDecidersOnlyOthers(List<CurriculumDecider> deciders) {
        checkValidDeciders(deciders);
        return repository.findByDeciderIn(deciders);
    }

    /**
     * TODO 예외 추후 정의
     */
    private void checkValidDecider(CurriculumDecider decider) {
        if (decider.majorType() != MajorType.주전공)
            throw new RuntimeException("주전공만 허용됩니다.");
    }

    /**
     * TODO 예외 추후 정의
     */
    private void checkValidDeciders(List<CurriculumDecider> deciders) {
        Set<Department> departments = new HashSet<>();
        for (CurriculumDecider decider : deciders) {
            if (decider.majorType() == MajorType.주전공) {
                throw new RuntimeException("주전공은 허용되지 않습니다.");
            }

            if (!departments.add(decider.department())) {
                throw new RuntimeException("동일한 학과(Department)가 여러 Decider에 존재합니다: " + decider.department().getName());
            }
        }
    }
}
