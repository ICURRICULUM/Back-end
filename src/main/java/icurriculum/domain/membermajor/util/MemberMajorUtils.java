package icurriculum.domain.membermajor.util;

import icurriculum.domain.membermajor.MemberMajor;

import java.util.List;
import java.util.stream.Collectors;

public class MemberMajorUtils {

    /**
     * 주전공 MemberMajor return
     */
    public static MemberMajor findMainMajor(List<MemberMajor> memberMajors) {
        return memberMajors.stream()
                .filter(MemberMajor::isMain)
                .findAny()
                .orElseThrow(RuntimeException::new);
        /**
         *  Todo 예외 추후 정의
         */
    }

    /**
     * 주전공 제외 MemberMajor return
     * 단일전공일 경우, 비어있는 List 반환
     */
    public static List<MemberMajor> findOtherMajors(List<MemberMajor> memberMajors) {
        return memberMajors.stream()
                .filter(m -> !m.isMain())
                .collect(Collectors.toList());
    }

}
