/*
package icurriculum.domain.categoryjudge;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.json.*;
import icurriculum.domain.take.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoryJudgeUtilsImpl implements CategoryJudgeUtils {
    @Override
    public Map<String, Category> judge(List<String> codes, Curriculum curriculum) {

        Map<String, Category> judgeCodes = new HashMap<>();

        CurriculumCodesJson curriculumCodesJson = curriculum.getCurriculumCodesJson(); // 교과과정

        Set<String> eMajor = curriculumCodesJson.findCodesByCategory(Category.전공필수);
        Set<String> cMajor = curriculumCodesJson.findCodesByCategory(Category.전공선택);
        Set<String> eGyo = curriculumCodesJson.findCodesByCategory(Category.교양필수);

        Set<String> creativityCodes = curriculum.getCreativityJson().getConfirmedCodes(); // 창의 영역 지정과목

        Set<String> swAiCodes = curriculum.getSwAiJson().getConfirmedCodes();
        Set<String> swAialternativeCodes = curriculum.getSwAiJson().getAlternativeCodes();

        Map<String, Set<String>> alternativeCourseMap = curriculum.getAlternativeCourseJson().getAlternativeCourseMap();

        for (String code : codes) {
            Category category = judgeCore(code, curriculum);  // 핵심교양 판단
            if (isCoreCode(category)) {
                judgeCodes.put(code, category);
            } else { // 핵심교양이 아닌 과목들의 다른영역 확인
                if (creativityCodes != null && creativityCodes.contains(code)) {
                    judgeCodes.put(code, Category.창의);
                } else if (((swAiCodes != null && swAiCodes.contains(code)) || (swAialternativeCodes != null && swAialternativeCodes.contains(code)))) {
                    judgeCodes.put(code, Category.SW_AI);
                } else if (eMajor.contains(code)) {
                    judgeCodes.put(code, Category.전공필수);
                } else if (cMajor.contains(code)) {
                    judgeCodes.put(code, Category.전공선택);
                } else if (eGyo.contains(code)) {
                    judgeCodes.put(code, Category.교양필수);
                } else {
                    if (alternativeCourseMap.containsKey(code)) {
                        judgeCodes.put(code, judgeAlternative(alternativeCourseMap.get(code), curriculum));
                    } else {
                        judgeCodes.put(code, Category.교양선택);
                    }
                }
            }

        }
        return judgeCodes;
    }

    */
/**
     * 사용자 핵심교양 판별 메서드
     *
     * @return 핵심교양 영역
     **//*

    public Category judgeCore(String code, Curriculum curriculum) {

        CoreJson coreJson = curriculum.getCoreJson(); // 핵심교양
        Boolean isAreaConfirmed = coreJson.getIsAreaConfirmed(); // 지정 영역이있는지 확인
        Set<Category> requiredAreas = coreJson.getRequiredAreas(); // 핵심교양 영역 리스트
        Map<Category, Set<String>> confirmedCodesByArea = coreJson.getConfirmedCodesByArea(); // 지정 영역 리스트
        Map<Category, Set<String>> alternativeCodesByArea = coreJson.getAlternativeCodesByArea(); //영역별 대체 리스트

        Set<String> alternativeCoreCodes = null;
        if (alternativeCodesByArea != null) {
            alternativeCoreCodes = alternativeCodesByArea.get(Category.핵심교양6);
        }
        Category category;

        if (code.startsWith("GEC") || code.startsWith("GED")) {
            char coreArea = code.charAt(3); // 핵교 영역
            switch (coreArea) {
                case '1' -> category = Category.핵심교양1;
                case '2' -> category = Category.핵심교양2;
                case '3' -> category = Category.핵심교양3;
                case '4' -> category = Category.핵심교양4;
                case '5' -> category = Category.핵심교양5;
                case '6' -> category = Category.핵심교양6;
                default -> category = Category.교양선택;
            }
        } else if (alternativeCoreCodes != null && alternativeCoreCodes.contains(code)) {
            return Category.핵심교양6;
        } else {
            if (confirmedCodesByArea != null) {
                for (Category tempCategory : confirmedCodesByArea.keySet()) {
                    Set<String> strings = confirmedCodesByArea.get(tempCategory);
                    if (strings.contains(code)) return tempCategory;
                }
            }
            return Category.교양선택;
        }
        if (!isAreaConfirmed) {
            if (confirmedCodesByArea != null && confirmedCodesByArea.containsKey(category)) {
                if (confirmedCodesByArea.get(category).contains(code)) {
                    return category;
                } else {
                    return Category.교양선택;
                }
            } else {
                return category;
            }
        } else {
            if (requiredAreas.contains(category)) {
                if (confirmedCodesByArea.containsKey(category)) {
                    if (confirmedCodesByArea.get(category).contains(code)) {
                        return category;
                    } else {
                        return Category.교양선택;
                    }
                } else {
                    return category;
                }
            }
        }

        return Category.교양선택;
    }

    public Boolean isCoreCode(Category category) {
        return !category.equals(Category.교양선택);
    }


    public Category judgeAlternative(Set<String> codes, Curriculum curriculum) {
        */
/**
         * @params codes : 해당과목의 대체가능한 과목들
         *//*

        CurriculumCodesJson curriculumCodesJson = curriculum.getCurriculumCodesJson(); // 교과과정

        Set<String> eMajor = curriculumCodesJson.findCodesByCategory(Category.전공필수);
        Set<String> cMajor = curriculumCodesJson.findCodesByCategory(Category.전공선택);
        Set<String> eGyo = curriculumCodesJson.findCodesByCategory(Category.교양필수);

        Set<String> creativityCodes = curriculum.getCreativityJson().getConfirmedCodes();

        Set<String> swAiCodes = curriculum.getSwAiJson().getConfirmedCodes();
        Set<String> swAialternativeCodes = curriculum.getSwAiJson().getAlternativeCodes();


        for (String code : codes) {
            Category category = judgeCore(code, curriculum);  // 핵심교양 판단
            if (isCoreCode(category)) {
                return category;
            } else { // 핵심교양이 아닌 과목들의 다른영역 확인
                if (creativityCodes != null && creativityCodes.contains(code)) {
                    return Category.창의;
                } else if (((swAiCodes != null && swAiCodes.contains(code)) || (swAialternativeCodes != null && swAialternativeCodes.contains(code)))) {
                    return Category.SW_AI;
                } else if (eMajor.contains(code)) {
                    return Category.전공필수;
                } else if (cMajor.contains(code)) {
                    return Category.전공선택;
                } else if (eGyo.contains(code)) {
                    return Category.교양필수;
                }
            }
        }
        return Category.교양선택;
    }

}
*/
