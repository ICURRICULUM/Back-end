package icurriculum.domain.graduation.processor;

import icurriculum.domain.curriculum.json.CreativityJson;
import icurriculum.domain.take.Take;
import icurriculum.domain.graduation.processor.dto.ProcessorDto.CreativeDto;


import java.util.List;

import static icurriculum.domain.take.util.TakeUtils.calculateTotalCredit;

public class CreativeProcessor implements Processor<CreativityJson, CreativeDto> {

    /**
     * 이수학점, 기준학점
     * 조건충족 여부 boolean
     */

    @Override
    public CreativeDto execute(CreativityJson creativityJson, List<Take> takes) {
        int completedCredit = calculateTotalCredit(takes);
        int requiredCredits = creativityJson.getRequiredCredit();
        boolean isClear = completedCredit >= requiredCredits;

        return new CreativeDto(completedCredit, requiredCredits, isClear);
    }

}
