package icurriculum.domain.graduation.processor;

import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.CoreDto;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.util.TakeUtils;

import java.util.*;
import java.util.stream.Collectors;

import static icurriculum.domain.take.Category.*;
import static icurriculum.domain.take.util.TakeUtils.calculateTotalCredit;

/**
 * 핵심교양
 * - category 는 학생의 주전공 상태에 따라서 이미 전처리된 상태라고 가정
 * - 1,2,4 영역을 들어야되는 학생이 핵심교양3을 들었으면 category 는 교양선택으로 되어 있어야함
 */

public class CoreProcessor implements Processor<CoreJson, CoreDto> {

    @Override
    public CoreDto execute(CoreJson coreJson, List<Take> takes) {
        List<Take> coreTakes = getCoreTakes(takes, coreJson.getAlternativeCodesByArea());
       
        int completedCredit = calculateTotalCredit(coreTakes);
        int requiredCredits = coreJson.getRequiredCredit();
        Set<Category> uncompletedArea = getUncompletedArea(coreJson, coreTakes);
        boolean isClear = checkClear(coreJson, completedCredit, requiredCredits, uncompletedArea);

        return new CoreDto(completedCredit, requiredCredits, uncompletedArea, isClear);
    }

    private List<Take> getCoreTakes(List<Take> allTakes, Map<Category, Set<String>> alternativeCodesByArea) {
        List<Take> coreTakes = TakeUtils.getCoreTakes(allTakes);
        checkAlternativeTakesByArea(핵심교양1, alternativeCodesByArea, coreTakes, allTakes);
        checkAlternativeTakesByArea(핵심교양2, alternativeCodesByArea, coreTakes, allTakes);
        checkAlternativeTakesByArea(핵심교양3, alternativeCodesByArea, coreTakes, allTakes);
        checkAlternativeTakesByArea(핵심교양4, alternativeCodesByArea, coreTakes, allTakes);
        checkAlternativeTakesByArea(핵심교양5, alternativeCodesByArea, coreTakes, allTakes);
        checkAlternativeTakesByArea(핵심교양6, alternativeCodesByArea, coreTakes, allTakes);
        return coreTakes;
    }

    private void checkAlternativeTakesByArea(Category category, Map<Category, Set<String>> alternativeCodesByArea, List<Take> coreTakes, List<Take> allTakes) {
        Set<String> alternativeCodes = alternativeCodesByArea.get(category);
        if (alternativeCodes == null)
            return;

        for (Take take : allTakes) {
            if (alternativeCodes.contains(take.getEffectiveCourse().getCode())) {
                Take modifiedTake = take.modifyCategory(category);
                coreTakes.add(modifiedTake);
            }
        }
    }

    private Set<Category> getUncompletedArea(CoreJson coreJson, List<Take> takes) {
        if (!coreJson.getIsAreaConfirmed()) {
            return Collections.emptySet();
        }

        Set<Category> completeCategories = takes.stream()
                .map(Take::getCategory)
                .collect(Collectors.toSet());

        Set<Category> notCompleteCategories = new HashSet<>();
        for (Category category : coreJson.getRequiredAreas()) {
            if (!completeCategories.contains(category)) {
                notCompleteCategories.add(category);
            }
        }
        return notCompleteCategories;
    }

    private boolean checkClear(CoreJson coreJson, int completedCredit, int requiredCredits, Set<Category> uncompletedArea) {
        if (!coreJson.getIsAreaConfirmed()) {
            return completedCredit >= requiredCredits;
        }
        return uncompletedArea.isEmpty();
    }

}