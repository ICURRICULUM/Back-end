package icurriculum.domain.categoryjudge;

import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.data.*;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import org.junit.jupiter.api.Test;


import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.take.Category;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryJudgeUtilsTest {


    private Curriculum curriculum;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 가상 데이터 설정
        curriculum = buildMockCurriculum();
    }

    @Test
    void testJudge_withMockCurriculumData() {
        List<String> codes = List.of("GEB1113", "GEB1115", "GEC4008", "GEG4007", "SOS1102", "SOS1111", "SWE1102",
                "GEB1114", "GEB1125", "GEB1131", "GEC1027", "SOS1101", "SWE1103",
                "ECO1101","GEE4014");

        Map<String, Category> result = CategoryJudgeUtils.judge(codes, curriculum);

        // 예상되는 결과를 검증
        assertEquals(Category.교양필수, result.get("GEB1113"));
        assertEquals(Category.교양필수, result.get("GEB1115"));
        assertEquals(Category.핵심교양4, result.get("GEC4008"));
        assertEquals(Category.교양선택, result.get("GEG4007"));
        assertEquals(Category.교양필수, result.get("SOS1102"));
        assertEquals(Category.교양필수, result.get("SOS1111"));
        assertEquals(Category.교양필수, result.get("GEB1114"));
        assertEquals(Category.전공필수, result.get("SWE1102"));
        assertEquals(Category.교양필수, result.get("GEB1125"));
        assertEquals(Category.교양필수, result.get("GEB1131"));
        assertEquals(Category.교양필수, result.get("SOS1101"));
        assertEquals(Category.핵심교양1, result.get("GEC1027"));
        assertEquals(Category.전공필수, result.get("SWE1103"));
        assertEquals(Category.교양선택, result.get("ECO1101"));
        assertEquals(Category.창의, result.get("GEE4014"));
    }

    // 가상 Curriculum 데이터를 빌드하는 메서드
    private Curriculum buildMockCurriculum() {
        // 핵심교양 데이터 설정
        Core core = Core.builder()
                .isAreaFixed(false)
                .requiredCredit(9)
                .requiredAreaSet(Collections.emptySet())
                .areaAlternativeCodeMap(Collections.emptyMap())
                .areaDeterminedCodeMap(Collections.emptyMap())
                .additionalInfoMap(Collections.emptyMap())
                .build();


        // SW_AI 데이터 설정
        SwAi swAi = SwAi.builder()
                .approvedCodeSet(Collections.emptySet())
                .areaAlternativeCodeSet(Collections.emptySet())
                .requiredCredit(0)
                .additionalInfoMap(Collections.emptyMap())
                .build();

        // 창의 데이터 설정
        Creativity creativity = Creativity.builder()
                .requiredCredit(3)
                .approvedCodeSet(Set.of("GEE4014"))
                .additionalInfoMap(Collections.emptyMap())
                .build();

        MajorRequired majorRequired = MajorRequired.builder()
                .codeSet(Set.of("SWE1102", "SWE1103", "SWE2101", "SWE2102", "SWE2103", "SWE2104", "SWE3101", "SWE3103", "SWE3104", "SWE4101", "SWE9000"))
                .additionalInfoMap(Collections.emptyMap())
                .build();

        MajorSelect majorSelect = MajorSelect.builder()
                .codeSet(Set.of(
                        "SWE2201", "SWE2202", "SWE2204", "SWE2205", "SWE3203", "SWE4201", "SWE4202",
                        "SWE4204", "SWE4205", "SWE2203", "SWE3105", "SWE3201", "SWE3202", "SWE3204",
                        "SWE3205", "SWE4203"
                ))
                .additionalInfoMap(Collections.emptyMap())
                .build();

        GeneralRequired generalRequired = GeneralRequired.builder()
                .codeSet(Set.of(
                        "GEB1113", "GEB1114", "GEB1115", "GEB1125", "GEB1131", "GEB1107",
                        "GEB1108", "GEB1109", "GEB1201", "GEB1202",
                        "GEB1203", "SOS1101", "SOS1102", "SOS1104", "SOS1109", "SOS1111"
                ))
                .additionalInfoMap(Collections.emptyMap())
                .build();

        // 대체과목 데이터 설정
        AlternativeCourse alternativeCourse = AlternativeCourse.builder()
                .alternativeCourseCodeMap(Collections.emptyMap())
                .build();

        // Curriculum 객체 빌드
        return Curriculum.builder()
                .decider(CurriculumDecider.builder()
                        .majorType(MajorType.주전공)
                        .departmentName(DepartmentName.컴퓨터공학과)
                        .joinYear(19)
                        .build())
                .core(core)
                .swAi(swAi)
                .creativity(creativity)
                .majorRequired(majorRequired)
                .majorSelect(majorSelect)
                .generalRequired(generalRequired)
                .requiredCredit(RequiredCredit.builder()
                        .totalNeedCredit(130)
                        .singleNeedCredit(65)
                        .secondNeedCredit(0)
                        .minorNeedCredit(0)
                        .build())
                .alternativeCourse(alternativeCourse)
                .build();
    }
}
