package icurriculum.domain.graduation.service.module.processor.majorselect;

import icurriculum.domain.graduation.service.module.processor.dto.ProcessorRequest.CreditWithData;
import icurriculum.domain.take.Take;
import java.util.Iterator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MajorSelectResult {

    private int totalMajorCompletedCredit; // 전공(전공선택 + 전공필수) 이수학점
    private int totalMajorRequiredCredit; // 전공(전공선택 + 전공필수) 필요학점
    private boolean isClear; // 조건 충족 여부

    public void initMajorSelectResult(CreditWithData creditWithData) {
        totalMajorCompletedCredit += creditWithData.majorRequiredCompletedCredit();
        totalMajorRequiredCredit += creditWithData.majorNeedCredit();
    }

    public void update(Take take, Iterator<Take> iterator) {
        totalMajorCompletedCredit += take.getEffectiveCourse().getCredit();
        iterator.remove();
    }

    public void checkIsClear() {
        isClear = totalMajorCompletedCredit >= totalMajorRequiredCredit;
    }
}
