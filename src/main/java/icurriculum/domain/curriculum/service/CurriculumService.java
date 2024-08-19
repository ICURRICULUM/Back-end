package icurriculum.domain.curriculum.service;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository repository;

    public Curriculum getCurriculumByDecider(CurriculumDecider decider) {
        return repository.findByDecider(decider)
                .orElseThrow(RuntimeException::new);
        /**
         * TODO 예외 추후 정의
         */
    }
}
