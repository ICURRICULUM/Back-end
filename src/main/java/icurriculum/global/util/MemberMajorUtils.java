package icurriculum.global.util;

import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.List;

public abstract class MemberMajorUtils {

    public static MemberMajor extractMemberMajorByMajorType(
        List<MemberMajor> memberMajorList,
        MajorType majorType
    ) {
        return memberMajorList.stream()
            .filter(mm -> mm.getMajorType() == majorType)
            .findAny()
            .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_MAJOR_NOT_FOUND));
    }

}
