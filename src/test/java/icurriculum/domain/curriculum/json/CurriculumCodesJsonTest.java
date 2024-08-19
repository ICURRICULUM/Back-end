package icurriculum.domain.curriculum.json;

import icurriculum.DataInitializer;
import icurriculum.domain.take.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CurriculumCodesJsonTest {

    @Autowired
    DataInitializer dataInitializer;
    CurriculumCodesJson testCurriculumCodesJson;

    @BeforeEach
    void beforeEach() {
        testCurriculumCodesJson = dataInitializer.getTestCurriculumCodesJson();
    }

    @Test
    @DisplayName("Category가 전공필수, 전공선택, 교양필수")
    public void findByCategory_정상Category() throws Exception {
        // when
        Set<String> 전공필수코드 = testCurriculumCodesJson.findByCategory(Category.전공필수);
        Set<String> 전공선택코드 = testCurriculumCodesJson.findByCategory(Category.전공선택);
        Set<String> 교양필수코드 = testCurriculumCodesJson.findByCategory(Category.교양필수);

        // then
        assertThat(전공필수코드).isNotEmpty();
        assertThat(전공선택코드).isNotEmpty();
        assertThat(교양필수코드).isNotEmpty();
    }

    @Test
    @DisplayName("Category가 전공필수, 전공선택, 교양필수 외 Category를 찾는 경우")
    public void findByCategory_비정상Category() throws Exception {
        // when & then
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.교양선택));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.SW_AI));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.창의));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.핵심교양1));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.핵심교양2));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.핵심교양3));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.핵심교양4));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.핵심교양5));
        assertThrows(RuntimeException.class,
                () -> testCurriculumCodesJson.findByCategory(Category.핵심교양6));

    }

}