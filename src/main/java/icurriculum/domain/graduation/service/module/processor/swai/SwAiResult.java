package icurriculum.domain.graduation.service.module.processor.swai;

import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.take.Take;
import java.util.Iterator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SwAiResult {

    private int completedCredit; // swAi 이수학점
    private int requiredCredit; // swAi 필요학점
    private boolean isClear; // 조건 충족 여부

    public void update(Take take, Iterator<Take> iterator, boolean isAreaAlternative) {
        completedCredit += take.getEffectiveCourse().getCredit();

        if (!isAreaAlternative) {
            iterator.remove();
        }
    }

    public void setRequiredCredit(SwAi swAi) {
        requiredCredit = swAi.getRequiredCredit();
    }

    public void checkIsClear() {
        isClear = completedCredit >= requiredCredit;
    }

}