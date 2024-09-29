package icurriculum.global.util;

import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.List;

public abstract class MemberMajorUtils {

    /*
     * return 주전공
     * 주전공 없을 시 에러 발생
     */
    public static MemberMajor extractMainMemberMajor(List<MemberMajor> memberMajorList) {
        return memberMajorList.stream()
            .filter(MemberMajor::isMain)
            .findAny()
            .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_MAIN_MAJOR_NOT_FOUND));
    }

}
