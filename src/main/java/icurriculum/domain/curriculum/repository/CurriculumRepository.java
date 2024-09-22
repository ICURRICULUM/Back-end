package icurriculum.domain.curriculum.repository;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    Optional<Curriculum> findByDecider(CurriculumDecider decider);

}
