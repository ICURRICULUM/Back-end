package icurriculum.domain.membermajor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.common.BaseRDBEntity;
import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 회원 전공 상태
 */

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class MemberMajor extends BaseRDBEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_major_id")
    @Getter
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private MajorType majorType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private MemberMajor(MajorType majorType, Department department, Member member) {
        this.majorType = majorType;
        this.department = department;
        this.member = member;
    }

}
