package icurriculum.global.util;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import java.util.EnumSet;
import java.util.Set;

public abstract class GraduationUtils {

    private static final int CURRICULUM_CHANGED_YEAR = 21;

    /*
     * 영어 심화
     */
    private static final Course 실용영어_LS_LEGACY = Course.builder()
        .name("실용영어 L/S")
        .code("GEB1201")
        .credit(3)
        .build();
    private static final Course 실용영어_RW_LEGACY = Course.builder()
        .name("실용영어 R/W")
        .code("GEB1202")
        .credit(3)
        .build();
    private static final Course 고급대학영어_LEGACY = Course.builder()
        .name("고급대학영어")
        .code("GEB1203")
        .credit(3)
        .build();

    private static final Course 실용영어_LS = Course.builder()
        .name("실용영어 L/S")
        .code("GEE3027")
        .credit(3)
        .build();
    private static final Course 실용영어_RW = Course.builder()
        .name("실용영어 R/W")
        .code("GEE3028")
        .credit(3)
        .build();
    private static final Course 고급대학영어 = Course.builder()
        .name("고급대학영어")
        .code("GEE3029")
        .credit(3)
        .build();

    /*
     * 영어 기초
     */
    private static final Course 의사소통_영어 = Course.builder()
        .name("의사소통 영어")
        .code("GEB1107")
        .credit(3)
        .build();
    private static final Course 의사소통_영어_중급 = Course.builder()
        .name("의사소통 영어: 중급")
        .code("GEB1108")
        .credit(3)
        .build();
    private static final Course 의사소통_영어_고급 = Course.builder()
        .name("의사소통 영어: 고급")
        .code("GEB1109")
        .credit(3)
        .build();

    public static final Set<Category> CORE_CATEGORYSET = EnumSet.of(
        Category.핵심교양1, Category.핵심교양2, Category.핵심교양3,
        Category.핵심교양4, Category.핵심교양5, Category.핵심교양6
    );

    public static boolean isApproved(Take take, Set<String> approvedCodeSet) {
        String takenCode = take.getEffectiveCourse().getCode();
        return approvedCodeSet.contains(takenCode);
    }

    public static boolean isCodeAlternative(
        Take take,
        Set<String> approvedCodeSet,
        AlternativeCourse alternativeCourse
    ) {
        String takenCode = take.getEffectiveCourse().getCode();
        Set<String> takenAltCodeSet = alternativeCourse.getAlternativeCodeSet(takenCode);

        for (String altCode : takenAltCodeSet) {
            if (approvedCodeSet.contains(altCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlreadyCheckedByAreaAlt(Take take, Set<Course> areaAltCourseSet) {
        return areaAltCourseSet.contains(take.getEffectiveCourse());
    }

    public static boolean isApprovedCategory(Take take, Category category) {
        return take.getCategory() == category;
    }

    public static boolean isCurriculumChanged(final int joinYear) {
        return joinYear >= CURRICULUM_CHANGED_YEAR;
    }

    public static boolean isBasicEnglishCourse(Course course) {
        return course.equals(GraduationUtils.의사소통_영어) ||
            course.equals(GraduationUtils.의사소통_영어_중급) ||
            course.equals(GraduationUtils.의사소통_영어_고급);
    }

    public static boolean isAdvanceEnglishCourse(Course course) {
        return course.equals(GraduationUtils.실용영어_LS_LEGACY) ||
            course.equals(GraduationUtils.실용영어_RW_LEGACY) ||
            course.equals(GraduationUtils.고급대학영어_LEGACY) ||
            course.equals(GraduationUtils.실용영어_LS) ||
            course.equals(GraduationUtils.실용영어_RW) ||
            course.equals(GraduationUtils.고급대학영어);
    }

    public static void removeBasicEnglishCourse(Set<Course> courseSet) {
        courseSet.remove(의사소통_영어);
        courseSet.remove(의사소통_영어_중급);
        courseSet.remove(의사소통_영어_고급);
    }

    public static void removeAdvanceEnglishCourse(Set<Course> courseSet) {
        courseSet.remove(실용영어_LS_LEGACY);
        courseSet.remove(실용영어_RW_LEGACY);
        courseSet.remove(고급대학영어_LEGACY);
        courseSet.remove(실용영어_LS);
        courseSet.remove(실용영어_RW);
        courseSet.remove(고급대학영어);
    }

}
