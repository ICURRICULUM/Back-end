package icurriculum.domain.curriculum.repository;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    Optional<Curriculum> findByDecider(CurriculumDecider decider);
}
