package icurriculum.domain.categoryjudge;

import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.take.Category;

import java.util.List;

public interface CategoryJudgeUtils {
    Category judge(List<MemberMajor> memberMajors, String code);
}
