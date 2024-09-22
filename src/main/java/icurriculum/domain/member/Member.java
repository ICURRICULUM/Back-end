package icurriculum.domain.member;

import icurriculum.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Member extends BaseTimeEntity {

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
