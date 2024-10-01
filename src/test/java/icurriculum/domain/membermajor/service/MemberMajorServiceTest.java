package icurriculum.domain.membermajor.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.global.response.exception.GeneralException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberMajorServiceTest {

    @Mock
    private MemberMajorRepository memberMajorRepository;

    @InjectMocks
    private MemberMajorService memberMajorService;

    private Member testMember;
    private Department testDepartment;
    private MemberMajor testMemberMajor;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
            .name("이승철")
            .joinYear(19)
            .build();

        testDepartment = Department.builder()
            .name(DepartmentName.컴퓨터공학과)
            .build();

        testMemberMajor = MemberMajor.builder()
            .department(testDepartment)
            .majorType(MajorType.주전공)
            .member(testMember)
            .build();
    }

    @Test
    @DisplayName("Member로 MemberMajor 리스트 조회 성공 테스트")
    void memberMajorListByMember_성공_테스트() {
        // given
        when(memberMajorRepository.findByMember(testMember))
            .thenReturn(List.of(testMemberMajor));

        // when
        List<MemberMajor> result = memberMajorService.getMemberMajorListByMember(testMember);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(testMemberMajor);
    }

    @Test
    @DisplayName("Member로 MemberMajor 리스트 조회 실패 테스트 - 데이터 없음")
    void memberMajorListByMember_실패_테스트() {
        // given
        when(memberMajorRepository.findByMember(testMember))
            .thenReturn(Collections.emptyList());

        // when & then
        assertThatThrownBy(() -> memberMajorService.getMemberMajorListByMember(testMember))
            .isInstanceOf(GeneralException.class);
    }

    @Test
    @DisplayName("Member로 MemberMajor 리스트 조회 실패 테스트 - 주전공 없음")
    void memberMajorListByMember_실패_테스트_주전공_없음() {
        // given
        testMemberMajor = MemberMajor.builder()
            .department(testDepartment)
            .member(testMember)
            .majorType(MajorType.복수전공)
            .build();

        when(memberMajorRepository.findByMember(testMember))
            .thenReturn(List.of(testMemberMajor));

        // when & then
        assertThatThrownBy(() -> memberMajorService.getMemberMajorListByMember(testMember))
            .isInstanceOf(GeneralException.class);
    }

    @Test
    @DisplayName("Member와 MajorType으로 MemberMajor 조회 성공 테스트")
    void memberMajorByMemberAndMajorType_성공_테스트() {
        // given
        when(memberMajorRepository.findByMemberAndMajorType(testMember, MajorType.주전공))
            .thenReturn(Optional.of(testMemberMajor));

        // when
        MemberMajor result = memberMajorService
            .getMemberMajorByMemberAndMajorType(testMember, MajorType.주전공);

        // then
        assertThat(result).isEqualTo(testMemberMajor);
    }

    @Test
    @DisplayName("Member와 MajorType으로 MemberMajor 조회 실패 테스트 - 데이터 없음")
    void memberMajorByMemberAndMajorType_실패_테스트() {
        // given
        when(memberMajorRepository.findByMemberAndMajorType(testMember, MajorType.주전공))
            .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(
            () -> memberMajorService.getMemberMajorByMemberAndMajorType(testMember, MajorType.주전공))
            .isInstanceOf(GeneralException.class);
    }
}
