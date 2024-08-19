package icurriculum.domain.membermajor.util;

import icurriculum.domain.membermajor.MemberMajor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static icurriculum.domain.membermajor.util.MemberMajorUtils.getExceptMainMemberMajor;
import static icurriculum.domain.membermajor.util.MemberMajorUtils.getMainMemberMajor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MemberMajorUtilsTest {

    @Mock
    MemberMajor mainMajor;
    @Mock
    MemberMajor minorMajor;

    @Mock
    MemberMajor secondMajor;

    @Test
    @DisplayName("주전공만 있는 상태일때 주전공 정상 리턴")
    public void getMainMemberMajor_주전공만포함상태() {
        // given
        when(mainMajor.isMain()).thenReturn(true);
        List<MemberMajor> memberMajors = Arrays.asList(mainMajor);

        // when
        MemberMajor expected = getMainMemberMajor(memberMajors);

        // then
        assertThat(mainMajor).isEqualTo(expected);
    }

    @Test
    @DisplayName("주전공 포함한 상태일때 주전공 정상 리턴")
    public void getMainMemberMajor_주전공포함상태() {
        // given
        when(mainMajor.isMain()).thenReturn(true);
        List<MemberMajor> memberMajors = Arrays.asList(mainMajor, minorMajor);

        // when
        MemberMajor expected = getMainMemberMajor(memberMajors);

        // then
        assertThat(mainMajor).isEqualTo(expected);
    }

    @Test
    @DisplayName("주전공 미포함한 상태일때 에러 발생")
    public void getMainMemberMajor_주전공미포함상태() {
        // given
        when(minorMajor.isMain()).thenReturn(false);
        List<MemberMajor> memberMajors = Arrays.asList(minorMajor);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            getMainMemberMajor(memberMajors);
        });
    }

    @Test
    @DisplayName("전공상태 empty 에러 발생")
    public void getMainMemberMajor_empty상태() {
        // given
        List<MemberMajor> memberMajors = new ArrayList<>();

        // when & then
        assertThrows(RuntimeException.class, () -> {
            getMainMemberMajor(memberMajors);
        });
    }

    @Test
    @DisplayName("주전공 포함한 상태일때 주전공 아닌 데이터들 정상 리턴")
    public void getExceptMainMemberMajor_주전공포함상태() {
        // given
        when(mainMajor.isMain()).thenReturn(true);
        when(minorMajor.isMain()).thenReturn(false);
        when(secondMajor.isMain()).thenReturn(false);
        List<MemberMajor> memberMajors = Arrays.asList(mainMajor, minorMajor, secondMajor);

        // when
        List<MemberMajor> exceptMainMemberMajors = getExceptMainMemberMajor(memberMajors);

        // then
        assertThat(exceptMainMemberMajors.size()).isEqualTo(2);
        assertThat(exceptMainMemberMajors).doesNotContain(mainMajor);
        assertThat(exceptMainMemberMajors).contains(minorMajor);
        assertThat(exceptMainMemberMajors).contains(secondMajor);
    }

    @Test
    @DisplayName("주전공만 있는 경우 비어있는 List 반환")
    public void getExceptMainMemberMajor_empty상태() {
        // given
        when(mainMajor.isMain()).thenReturn(true);
        List<MemberMajor> memberMajors = Arrays.asList(mainMajor);

        // when
        List<MemberMajor> exceptMainMemberMajors = getExceptMainMemberMajor(memberMajors);

        // then
        assertThat(exceptMainMemberMajors).isEmpty();
    }
}
