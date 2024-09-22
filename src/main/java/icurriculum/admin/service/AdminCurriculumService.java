package icurriculum.admin.service;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminCurriculumService {

    private final CurriculumRepository repository;

    public Curriculum getCurriculumByDecider(CurriculumDecider decider) {
        return repository.findByDecider(decider)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = false) // 쓰기 작업 이므로 읽기 전용 해제
    public void modifyCurriculum(Curriculum curriculumData, Curriculum modifyCurriculum){
        modifyCurriculum.setCoreJson(curriculumData.getCoreJson());
        modifyCurriculum.setSwAiJson(curriculumData.getSwAiJson());
        modifyCurriculum.setCreativityJson(curriculumData.getCreativityJson());
        modifyCurriculum.setRequiredCreditJson(curriculumData.getRequiredCreditJson());
        modifyCurriculum.setCurriculumCodesJson(curriculumData.getCurriculumCodesJson());
        modifyCurriculum.setAlternativeCourseJson(curriculumData.getAlternativeCourseJson());
        repository.save(modifyCurriculum);
    }
}
