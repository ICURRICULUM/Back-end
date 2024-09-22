package icurriculum.domain.department;

import icurriculum.data.TestDataInitializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static icurriculum.data.TestDataInitializer.*;
import static org.assertj.core.api.Assertions.assertThat;

class DepartmentTest {
    private final TestDataInitializer dataInitializer = new TestDataInitializer();

    @Test
    @DisplayName("Department 객체가 정상적으로 생성되는지 테스트")
    void 학과객체생성_테스트() {
        // given
        Department departmentData = dataInitializer.getDepartmentData();

        // when
        DepartmentName departmentName = departmentData.getName();

        // then
        assertThat(departmentName).isEqualTo(DEPARTMENT_NAME_컴공);
    }

}