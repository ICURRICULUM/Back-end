package icurriculum.domain.graduation.processor;

import icurriculum.domain.curriculum.json.SwAiJson;
import icurriculum.domain.take.Take;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.SwAiDto;

import java.util.List;

import static icurriculum.domain.take.util.TakeUtils.calculateTotalCredit;

public class SwAiProcessor implements Processor<SwAiJson, SwAiDto> {


    /**
     * 이수학점, 기준학점
     * 조건충족 여부 boolean
     */

    public SwAiDto execute(SwAiJson swAiJson, List<Take> takes) {
        int completedCredit = calculateTotalCredit(takes);
        int requiredCredits = swAiJson.getRequiredCredit();
        boolean isClear = completedCredit >= requiredCredits;

        return new SwAiDto(completedCredit, requiredCredits, isClear);
    }

}
