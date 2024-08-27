package icurriculum.domain.take.service;

import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.CustomCourse;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TakeServiceTest {

    @Mock
    private TakeRepository takeRepository;

    @InjectMocks
    private TakeService takeService;

    private Member testMember;
    private Course course;
    private CustomCourse customCourse;

    @BeforeEach
    void setUp() {
        testMember = Member.builder().name("이승철").joinYear(19).role(RoleType.ROLE_USER).build();
        course = Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build();
        customCourse = new CustomCourse("CSE9318", "현장실습 18", 18);
    }

    @Test
    @DisplayName("take 데이터 존재, Take List 정상 반환")
    void findTakesByMember_shouldReturnListOfTakes() {
        // given
        List<Take> mockTakes = List.of(
                Take.builder().category(Category.교양필수).takenYear("2019").takenSemester("1").member(testMember).course(course).build(),
                Take.builder().category(Category.전공선택).takenYear("2023").takenSemester("2").member(testMember).customCourse(customCourse).build()
        );
        when(takeRepository.findByMember(testMember)).thenReturn(mockTakes);

        // when
        List<Take> result = takeService.findTakesByMember(testMember);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsAll(mockTakes);
    }

    @Test
    @DisplayName("take 데이터가 없을 때, 비어있는 Take List 반환")
    void findTakesByMember_shouldReturnEmptyListWhenNoTakesFound() {
        // given
        when(takeRepository.findByMember(testMember)).thenReturn(List.of());

        // when
        List<Take> result = takeService.findTakesByMember(testMember);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

}
