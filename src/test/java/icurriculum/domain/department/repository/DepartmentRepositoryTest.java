package icurriculum.domain.department.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import icurriculum.data.TestDataInitializer;
import icurriculum.domain.department.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    private TestDataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        dataInitializer = new TestDataInitializer(departmentRepository);
        dataInitializer.initDepartmentData();
    }

    @Test
    @DisplayName("학과 중복 저장 시 에러 발생 테스트")
    void 중복_학과_중복저장_테스트_Unique_제약_에러발생() {
        // given
        Department department = dataInitializer.getDepartmentData();

        // when & then
        assertThrows(DataIntegrityViolationException.class,
            () -> departmentRepository.save(department));
    }

}