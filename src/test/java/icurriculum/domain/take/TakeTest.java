package icurriculum.domain.take;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.exception.GeneralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TakeTest {

    Member testMember;
    Course course;
    CustomCourse customCourse;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
            .name("이승철")
            .joinYear(19)
            .role(RoleType.ROLE_USER)
            .build();

        course = Course.builder()
            .code("GEB1112")
            .name("크로스오버 1 : 인간의 탐색")
            .credit(2)
            .build();

        customCourse = CustomCourse.builder()
            .name("현장실습 18")
            .credit(18)
            .build();
    }

    @Test
    @DisplayName("Take 객체 생성 테스트 - 정상적인 Course 할당")
    void take_객체_생성_테스트_Course_할당() {
        // when
        Take take = Take.builder()
            .category(Category.전공필수)
            .takenYear("23")
            .takenSemester("1")
            .member(testMember)
            .course(course)
            .majorType(MajorType.주전공)
            .build();

        // then
        assertThat(take.getEffectiveCourse()).isEqualTo(course);
    }

    @Test
    @DisplayName("Take 객체 생성 테스트 - CustomCourse 할당")
    void take_객체_생성_테스트_CustomCourse_할당() {
        // when
        Take take = Take.builder()
            .category(Category.전공선택)
            .takenYear("23")
            .takenSemester("1")
            .member(testMember)
            .customCourse(customCourse)
            .majorType(MajorType.주전공)
            .build();

        // then
        assertThat(take.getEffectiveCourse().getCode()).isEqualTo(customCourse.getCode());
        assertThat(take.getEffectiveCourse().getName()).isEqualTo(customCourse.getName());
        assertThat(take.getEffectiveCourse().getCredit()).isEqualTo(customCourse.getCredit());
    }

    @Test
    @DisplayName("Take 객체 생성 실패 테스트 - Course와 CustomCourse 둘 다 null 또는 둘 다 존재할 때 예외 발생")
    void take_객체_생성_실패_테스트() {
        // when & then : 둘 다 null일 때 예외 발생
        assertThatThrownBy(
            () -> Take.builder()
                .category(Category.전공필수)
                .takenYear("23")
                .takenSemester("1")
                .member(testMember)
                .majorType(MajorType.주전공)
                .build()
        ).isInstanceOf(GeneralException.class);

        // when & then :  둘 다 존재할 때 예외 발생
        assertThatThrownBy(
            () -> Take.builder()
                .category(Category.전공필수)
                .takenYear("23")
                .takenSemester("1")
                .member(testMember)
                .course(course)
                .customCourse(customCourse)
                .majorType(MajorType.주전공)
                .build()
        ).isInstanceOf(GeneralException.class);
    }

}
