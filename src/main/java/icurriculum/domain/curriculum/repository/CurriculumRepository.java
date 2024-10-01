package icurriculum.domain.curriculum.repository;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurriculumRepository extends MongoRepository<Curriculum, String> {

    Optional<Curriculum> findByDecider(CurriculumDecider decider);

}
