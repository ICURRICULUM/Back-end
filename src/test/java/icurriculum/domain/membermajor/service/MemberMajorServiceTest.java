package icurriculum.domain.membermajor.service;

import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static icurriculum.domain.member.RoleType.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberMajorServiceTest {

    @Mock
    private MemberMajorRepository memberMajorRepository;

    @InjectMocks
    private MemberMajorService memberMajorService;

    private Member testMember;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = Department.builder()
                .name(컴퓨터공학과)
                .build();

        testMember = Member.builder()
                .name("이승철")
                .joinYear(2019)
                .role(ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("주어진 회원으로 전공 상태 목록을 반환해야 한다.")
    void findMajorsByMember_shouldReturnMemberMajors() {
        // given
        List<MemberMajor> mockMemberMajors = List.of(
                MemberMajor.builder()
                        .majorType(MajorType.주전공)
                        .member(testMember)
                        .department(testDepartment)
                        .build(),
                MemberMajor.builder()
                        .majorType(MajorType.복수전공)
                        .member(testMember)
                        .department(testDepartment)
                        .build()
        );
        when(memberMajorRepository.findByMember(testMember)).thenReturn(mockMemberMajors);

        // when
        List<MemberMajor> result = memberMajorService.findMajorsByMember(testMember);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(mockMemberMajors.size());
        assertThat(result).containsAll(mockMemberMajors);
    }

    @Test
    @DisplayName("주어진 회원에게 전공 상태가 없으면 빈 목록을 반환해야 한다.")
    void findMajorsByMember_shouldReturnEmptyListForMemberWithNoMajors() {
        // given
        when(memberMajorRepository.findByMember(testMember)).thenReturn(List.of());

        // when
        List<MemberMajor> result = memberMajorService.findMajorsByMember(testMember);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
}
