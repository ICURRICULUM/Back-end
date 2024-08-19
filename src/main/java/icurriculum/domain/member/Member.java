package icurriculum.domain.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    private Integer joinYear;

    @Enumerated(STRING)
    private RoleType role;

    @Builder
    public Member(String name, Integer joinYear, RoleType role) {
        this.name = name;
        this.joinYear = joinYear;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", joinYear=" + joinYear +
                ", role=" + role +
                '}';
    }
}
