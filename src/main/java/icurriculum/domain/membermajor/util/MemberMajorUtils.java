package icurriculum.domain.membermajor.util;

import icurriculum.domain.membermajor.MemberMajor;

import java.util.List;
import java.util.stream.Collectors;

public class MemberMajorUtils {

    /**
     * 주전공 MemberMajor return
     */
    public static MemberMajor getMainMemberMajor(List<MemberMajor> memberMajors) {
        return memberMajors.stream()
                .filter(MemberMajor::isMain)
                .findAny()
                .orElseThrow(RuntimeException::new); // 예외 추후 정의
    }

    /**
     * 주전공 제외 MemberMajor return
     */
    public static List<MemberMajor> getExceptMainMemberMajor(List<MemberMajor> memberMajors) {
        return memberMajors.stream()
                .filter(m -> !m.isMain())
                .collect(Collectors.toList());
    }

}
