package icurriculum.domain.member;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import icurriculum.global.common.BaseRDBEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Member extends BaseRDBEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer joinYear;

    @Enumerated(STRING)
    private RoleType role;

    @Builder
    public Member(String name, Integer joinYear, RoleType role) {
        this.name = name;
        this.joinYear = joinYear;
        this.role = role;
    }

}
