package icurriculum.domain.graduation.service.module.processor.core;

import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoreResult {

    private int completedCredit; // 핵심교양 이수학점
    private int requiredCredit; // 핵심교양 필요학점
    private final Set<Category> uncompletedArea = new HashSet<>(); // 미이수 영역
    private boolean isClear; // 조건 충족 여부

    public void initUncompletedArea(Core core) {
        uncompletedArea.addAll(core.getRequiredAreaSet());
    }

    public void update(
        Take take,
        Iterator<Take> iterator,
        Category area,
        boolean isAreaAlternative
    ) {
        completedCredit += take.getEffectiveCourse().getCredit();
        uncompletedArea.remove(area);

        if (!isAreaAlternative) {
            iterator.remove();
        }
    }

    public void setRequiredCredit(Core core) {
        this.requiredCredit = core.getRequiredCredit();
    }

    public void checkIsClear(Core core) {
        if (!core.getIsAreaFixed()) {
            isClear = completedCredit >= requiredCredit;
            return;
        }
        isClear = uncompletedArea.isEmpty();
    }
}
