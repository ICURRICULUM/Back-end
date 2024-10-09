package icurriculum.domain.graduation.service.module.processor.creativity;

import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.take.Take;
import java.util.Iterator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreativityResult {

    int completedCredit; // 창의 이수학점
    int requiredCredit; // 창의 필요학점
    boolean isClear; // 조건 충족 여부

    public void update(Take take, Iterator<Take> iterator) {
        completedCredit += take.getEffectiveCourse().getCredit();
        iterator.remove();
    }

    public void setRequiredCredit(Creativity creativity) {
        requiredCredit = creativity.getRequiredCredit();
    }

    public void checkIsClear() {
        isClear = completedCredit >= requiredCredit;
    }
}

