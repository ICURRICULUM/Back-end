package icurriculum.domain.curriculum.repository;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.data.TestDataInitializer;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CurriculumRepositoryTest {

    @Autowired
    private CurriculumRepository curriculumRepository;

    private TestDataInitializer dataInitializer;

    private Curriculum curriculum;

    @BeforeEach
    void setUp() {
        dataInitializer = new TestDataInitializer(curriculumRepository);
        curriculum = dataInitializer.initCurriculumData();
    }


    @Test
    @DisplayName("findByDecider 테스트")
    void findByDecider_테스트() {
        // given
        CurriculumDecider decider = dataInitializer.getCurriculumData().getDecider();

        // when
        Optional<Curriculum> foundCurriculum = curriculumRepository.findByDecider(decider);

        // then
        assertThat(foundCurriculum).isPresent();
        assertThat(foundCurriculum.get().getDecider()).isEqualTo(curriculum.getDecider());
        assertThat(foundCurriculum.get().getCoreJson()).isEqualTo(curriculum.getCoreJson());
    }


    @Test
    @DisplayName("findByDecider 미존재 Decider 테스트")
    void findByDecider_미존재_테스트() {
        // given
        CurriculumDecider nonExistingDecider = new CurriculumDecider(MajorType.주전공,
            DepartmentName.전기공학과, 19);

        // when
        Optional<Curriculum> foundCurriculum = curriculumRepository.findByDecider(
            nonExistingDecider);

        // then
        assertThat(foundCurriculum).isEmpty();
    }

}
