package icurriculum.domain.curriculum.service;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Optional;
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
    public CurriculumDecider convertToDecider(MajorType majorType, DepartmentName departmentName, Integer joinYear) {
        return CurriculumDecider.builder().majorType(majorType)
                .departmentName(departmentName)
                .joinYear(joinYear).build();
    }

    public String createCurriculum(Curriculum curriculum, CurriculumDecider decider){
        Optional<Curriculum> existingCurriculumOptional = repository.findByDecider(decider);
        if(existingCurriculumOptional.isPresent()){
            return "duplicate";
        }
        Curriculum newCurriculum = Curriculum.builder()
                .decider(decider)
                .core(curriculum.getCore())
                .creativity(curriculum.getCreativity())
                .swAi(curriculum.getSwAi())
                .generalRequired(curriculum.getGeneralRequired())
                .majorRequired(curriculum.getMajorRequired())
                .majorSelect(curriculum.getMajorSelect())
                .requiredCredit(curriculum.getRequiredCredit())
                .alternativeCourse(curriculum.getAlternativeCourse())
                .build();
        repository.save(newCurriculum);
        return "새로운 커리큘럼 등록 성공!";
    }

    public void deleteCurriculum(CurriculumDecider curriculumDecider){
        Curriculum curriculum = getCurriculumByDecider(curriculumDecider);
        repository.delete(curriculum);
    }

    public void updateCurriculum(Curriculum curriculum, CurriculumDecider decider) {
        Curriculum existingCurriculum = getCurriculumByDecider(decider);

        System.out.println(curriculum);
        existingCurriculum.update(curriculum);
        repository.save(existingCurriculum);
    }

}
