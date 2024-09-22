package icurriculum.domain.member.repository;

import icurriculum.data.TestDataInitializer;
import icurriculum.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private TestDataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        dataInitializer = new TestDataInitializer(memberRepository);
        dataInitializer.initMemberData();
    }

    @Test
    @DisplayName("저장된 Member 객체가 Repository에 저장되었는지 테스트")
    void 저장된_멤버_확인_테스트() {
        // given
        Member member = dataInitializer.getMemberData();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getName()).isEqualTo(member.getName());
        assertThat(savedMember.getJoinYear()).isEqualTo(member.getJoinYear());
        assertThat(savedMember.getRole()).isEqualTo(member.getRole());
    }

    @Test
    @DisplayName("모든 멤버 조회 테스트")
    void 모든_멤버_조회_테스트() {
        // given
        dataInitializer.initMemberData();

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members).isNotEmpty();
    }
}
