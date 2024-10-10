package icurriculum.domain.take.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.CustomCourse;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TakeServiceTest {

    @Mock
    private TakeRepository takeRepository;

    @InjectMocks
    private TakeService takeService;

    private Member testMember;
    private Course course;
    private CustomCourse customCourse;
    private Take take;
    private Take customTake;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
            .name("이승철")
            .joinYear(19)
            .role(RoleType.ROLE_USER)
            .build();

        course = Course.builder()
            .code("CSE1101")
            .name("객체지향프로그래밍")
            .credit(3)
            .build();

        customCourse = CustomCourse.builder()
            .name("현장실습 18")
            .credit(18)
            .build();

        take = Take.builder()
            .category(Category.전공필수)
            .takenYear("23")
            .takenSemester("1")
            .member(testMember)
            .course(course)
            .majorType(MajorType.주전공)
            .build();

        customTake = Take.builder()
            .category(Category.전공선택)
            .takenYear("23")
            .takenSemester("2")
            .member(testMember)
            .customCourse(customCourse)
            .majorType(MajorType.부전공)
            .build();
    }

    @Test
    @DisplayName("Member로 Take 리스트를 조회하는 테스트")
    void 멤버로_수강_리스트_조회_테스트() {
        // given
        when(takeRepository.findByMember(testMember))
            .thenReturn(List.of(take, customTake));

        // when
        List<Take> takeList = takeService.getTakeListByMember(testMember);

        // then
        assertThat(takeList).isNotEmpty();
        assertThat(takeList).containsExactly(take, customTake);
        assertThat(takeList).allMatch(take -> take.getMember().equals(testMember));
    }

    @Test
    @DisplayName("Member와 MajorType으로 Take 리스트를 조회하는 테스트")
    void 멤버와_전공유형으로_수강_리스트_조회_테스트() {
        // given
        MajorType majorType = MajorType.주전공;
        when(takeRepository.findByMemberAndMajorType(testMember, majorType))
            .thenReturn(List.of(take));

        // when
        List<Take> takeList = takeService.getTakeListByMemberAndMajorType(testMember, majorType);

        // then
        assertThat(takeList).isNotEmpty();
        assertThat(takeList).containsExactly(take);
        assertThat(takeList).allMatch(take -> take.getMajorType().equals(majorType));
        assertThat(takeList).extracting(Take::getEffectiveCourse).containsExactly(course);
    }
}

