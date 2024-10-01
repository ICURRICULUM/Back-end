package icurriculum.domain.curriculum.service;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCurriculumService {

    private final CurriculumRepository repository;

    public Curriculum getCurriculumByDecider(CurriculumDecider decider) {
        Curriculum curriculum = repository.findByDecider(decider)
            .orElseThrow(() -> new GeneralException(ErrorStatus.CURRICULUM_NOT_FOUND));
        curriculum.validate();

        return curriculum;
    }

}
