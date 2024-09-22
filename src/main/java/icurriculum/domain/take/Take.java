package icurriculum.domain.take;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.common.BaseTimeEntity;
import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Take extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "take_id")
    private Long id;

    @Getter
    @Enumerated(STRING)
    @Column(nullable = false)
    private Category category;

    private String takenYear;

    private String takenSemester;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Getter
    private Member member;

    /**
     * Course Table 에 존재하는 Take nullable
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    /**
     * 사용자가 직접 custom 입력한 Take
     */
    @Embedded
    private CustomCourse customCourse;

    /**
     * 사용자가 어떤 상태의 전공으로 들은 수업인지 분류
     */
    @Getter
    @Enumerated(STRING)
    @Column(nullable = false)
    private MajorType majorType;

    /**
     * 객체 생성 시 valid 로직 수행
     */

    @Builder
    public Take(Category category, String takenYear, String takenSemester, Member member,
        Course course, CustomCourse customCourse, MajorType majorType) {
        this.category = category;
        this.takenYear = takenYear;
        this.takenSemester = takenSemester;
        this.member = member;
        this.course = course;
        this.customCourse = customCourse;
        this.majorType = majorType;

        checkValidTake();
    }

    /**
     * 비즈니스 메소드
     * - checkValidTake
     * - getEffectiveCourse
     */

    /**
     * Course 또는 CustomCourse 중 하나만 채워져 있어야 한다.
     */
    private void checkValidTake() {
        if ((course == null && customCourse == null) || (course != null && customCourse != null)) {
            throw new GeneralException(ErrorStatus.TAKE_HAS_ABNORMAL_COURSE);
        }
    }

    /**
     * Take 에서 Course 를 가져올 때 해당 메소드를 사용
     */
    public Course getEffectiveCourse() {
        if (course != null) {
            return course;
        }
        if (customCourse != null) {
            return new Course(
                customCourse.getCode(),
                customCourse.getName(),
                customCourse.getCredit()
            );
        }
        throw new GeneralException(ErrorStatus.TAKE_HAS_ABNORMAL_COURSE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Take take = (Take) o;
        return Objects.equals(id, take.id) && getCategory() == take.getCategory() && Objects.equals(
            takenYear, take.takenYear) && Objects.equals(takenSemester, take.takenSemester)
            && Objects.equals(getMember(), take.getMember()) && Objects.equals(getEffectiveCourse(),
            take.getEffectiveCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getCategory(), takenYear, takenSemester, getMember(),
            getEffectiveCourse());
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
            ", majorType=" + majorType +
            '}';
    }
}
