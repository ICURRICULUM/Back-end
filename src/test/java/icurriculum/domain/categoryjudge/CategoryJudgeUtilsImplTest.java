package icurriculum.domain.categoryjudge;

import org.junit.jupiter.api.Test;


import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.json.*;
import icurriculum.domain.take.Category;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryJudgeUtilsImplTest {

    private CategoryJudgeUtilsImpl categoryJudgeUtils;

    private Curriculum curriculum;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryJudgeUtils = new CategoryJudgeUtilsImpl();

        // 가상 데이터 설정
        curriculum = buildMockCurriculum();
    }

    @Test
    void testJudge_withMockCurriculumData() {
        List<String> codes = List.of("CEE1101", "GED3101", "CRE4302", "CSE1101", "ICE4029", "GEB1201","GED6101","ACE0920");

        Map<String, Category> result = categoryJudgeUtils.judge(codes, curriculum);

        // 예상되는 결과를 검증
        assertEquals(Category.핵심교양3, result.get("GED3101"));
        assertEquals(Category.교양선택, result.get("GED6101"));
        assertEquals(Category.핵심교양6, result.get("ACE0920"));
        assertEquals(Category.창의, result.get("CRE4302"));
        assertEquals(Category.SW_AI, result.get("CEE1101"));
        assertEquals(Category.전공필수, result.get("CSE1101"));
        assertEquals(Category.전공선택, result.get("ICE4029"));
        assertEquals(Category.교양필수, result.get("GEB1201"));
    }

    // 가상 Curriculum 데이터를 빌드하는 메서드
    private Curriculum buildMockCurriculum() {
        // 핵심교양 데이터 설정
        CoreJson coreJson = new CoreJson(
                false, 9,
                null,
                Map.of(
                        Category.핵심교양6,Set.of("ACE0920")
                ),
                null
        );

        // SW_AI 데이터 설정
        SwAiJson swAiJson = new SwAiJson(
                true,
                Set.of("CEE1101"),
                null,
                3
        );

        // 창의 데이터 설정
        CreativityJson creativityJson = new CreativityJson(
                true,
                Set.of("CRE4302"),
                3
        );

        // 교과과정 데이터 설정
        CurriculumCodesJson curriculumCodesJson = new CurriculumCodesJson(
                Map.of(
                        Category.전공필수, Set.of("CSE1101", "CSE1102", "CSE1103", "CSE2101", "CSE2112", "CSE4205"),
                        Category.전공선택, Set.of( "ICE4029", "IEN3204", "CSE1105", "CSE2103", "CSE2104", "CSE2105", "CSE2107",
                                "CSE3101", "CSE3201", "CSE3203", "CSE3206", "CSE3308", "CSE3309", "CSE4201",
                                "CSE4202", "CSE4204", "CSE4308", "CSE3102", "CSE3207", "CSE4302", "CSE4305",
                                "CSE4314", "CSE3202", "CSE3205", "CSE3302", "CSE3303", "CSE3304", "CSE3307",
                                "CSE4312", "CSE3204", "CSE4301", "CSE4303", "CSE4304", "CSE4307"),
                        Category.교양필수, Set.of( "GEB1112", "GEB1114", "GEB1115", "GEB1124", "GEB1131", "GEB1107",
                                "GEB1201", "ACE1204", "ACE1312", "ACE2101",
                                "ACE2104", "ACE2106", "MTH1001", "MTH1002", "PHY1001", "PHY1002", "PHY1003", "PHY1004")
                )
        );

        // 대체과목 데이터 설정
        AlternativeCourseJson alternativeCourseJson = new AlternativeCourseJson(
                Map.of(
                        "CSE2013", Set.of("CSE3209"),
                        "CSE3209", Set.of("CSE2013")
                )
        );

        // Curriculum 객체 빌드
        return Curriculum.builder()
                .decider(null)
                .coreJson(coreJson)
                .swAiJson(swAiJson)
                .creativityJson(creativityJson)
                .requiredCreditJson(null)
                .curriculumCodesJson(curriculumCodesJson)
                .alternativeCourseJson(alternativeCourseJson)
                .build();
    }
}
