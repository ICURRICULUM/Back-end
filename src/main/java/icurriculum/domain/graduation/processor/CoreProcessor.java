package icurriculum.domain.graduation.processor;

import icurriculum.domain.curriculum.json.CoreJson;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.CoreDto;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static icurriculum.domain.take.util.TakeUtils.calculateTotalCredit;

/**
 * 핵심교양
 * - category 는 학생의 주전공 상태에 따라서 이미 전처리된 상태라고 가정
 * - 1,2,4 영역을 들어야되는 학생이 핵심교양3을 들었으면 category 는 교양선택으로 되어 있어야함
 * - 1,2,4 영역을 들어야되는 학생이 핵심교양1을 2개를 들었으면 한 개만 핵심교양1이라고 인정되고 나머지 하나는 교양선택으로 되어 있어야함
 */

public class CoreProcessor implements Processor<CoreJson, CoreDto> {

    @Override
    public CoreDto execute(CoreJson coreJson, List<Take> takes) {
        int completedCredit = calculateTotalCredit(takes);
        int requiredCredits = coreJson.getRequiredCredit();
        Set<Category> uncompletedArea = getUncompletedArea(coreJson, takes);
        boolean isClear = checkClear(coreJson, completedCredit, requiredCredits, uncompletedArea);

        return new CoreDto(completedCredit, requiredCredits, uncompletedArea, isClear);
    }

    private boolean isAreaConfirmed(CoreJson coreJson) {
        return coreJson.getIsAreaConfirmed();
    }

    private Set<Category> getUncompletedArea(CoreJson coreJson, List<Take> takes) {
        if (!isAreaConfirmed(coreJson)) {
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
        if (!isAreaConfirmed(coreJson)) {
            return completedCredit >= requiredCredits;
        }
        return uncompletedArea.isEmpty();
    }

}