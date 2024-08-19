package icurriculum.domain.take;

import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Take {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "take_id")
    private Long id;

    @Enumerated(STRING)
    private Category category;

    private String takenYear;

    private String takenSemester;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 사용자가 custom 한 과목일 경우가 있다.
     * NullPointException 주의
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * 현장실습과 같이 사용자가 직접 입력한 경우
     */
    @Embedded
    private CustomCourse customCourse;

    @Builder
    public Take(Category category, String takenYear, String takenSemester, Member member, Course course, CustomCourse customCourse) {
        this.category = category;
        this.takenYear = takenYear;
        this.takenSemester = takenSemester;
        this.member = member;
        this.course = course;
        this.customCourse = customCourse;
    }

    @Override
    public String toString() {
        return "Take{" +
                "id=" + id +
                ", category=" + category +
                ", takenYear='" + takenYear + '\'' +
                ", takenSemester='" + takenSemester + '\'' +
                ", member=" + member +
                ", course=" + course +
                ", customCourse=" + customCourse +
                '}';
    }
}
