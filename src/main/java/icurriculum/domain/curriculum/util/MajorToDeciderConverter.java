package icurriculum.domain.curriculum.util;

import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.membermajor.MemberMajor;

import java.util.List;

public class MajorToDeciderConverter {

    /**
     * MemberMajor -> CurriculumDecider
     */
    public static CurriculumDecider toDecider(MemberMajor memberMajor) {
        return new CurriculumDecider(
                memberMajor.getMajorType(),
                memberMajor.getDepartment(),
                memberMajor.getMember().getJoinYear()
        );
    }

    /**
     * MemberMajor List -> CurriculumDecider List
     */
    public static List<CurriculumDecider> toDeciders(List<MemberMajor> memberMajors) {
        return memberMajors.stream()
                .map(MajorToDeciderConverter::toDecider)
                .toList();
    }
}
