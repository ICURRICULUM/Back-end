package icurriculum.domain.take;

import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static icurriculum.domain.course.util.CourseUtils.convertCustomToCourse;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Take {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "take_id")
    private Long id;

    @Getter
    @Enumerated(STRING)
    private Category category;

    private String takenYear;

    private String takenSemester;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @Getter
    private Member member;

    /**
     * 사용자가 custom 한 과목일 경우가 있다.
     * custom 과목은 Course 가 비어있을 수 있음
     * NullPointException 주의
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * 사용자가 직접 custom 입력한 경우
     * ex. 현장실습-16학점-전공필수
     */
    @Embedded
    private CustomCourse customCourse;

    @Builder
    public Take(Category category, String takenYear, String takenSemester, Member member, Course course, CustomCourse customCourse) {
        /**
         * Todo 예외 추후 정의
         * Course 또는 CustomCourse 중 하나만 채워져 있어야 한다.
         */
        if ((course == null && customCourse == null) || (course != null && customCourse != null)) {
            throw new RuntimeException();
        }

        this.category = category;
        this.takenYear = takenYear;
        this.takenSemester = takenSemester;
        this.member = member;
        this.course = course;
        this.customCourse = customCourse;
    }

    /**
     * Todo 예외 추후 정의
     * Take 에서 Course 를 가져올 때 해당 메소드를 사용
     */
    public Course getEffectiveCourse() {
        if (course != null) {
            return course;
        }
        if (customCourse != null) {
            return convertCustomToCourse(customCourse);
        }
        throw new RuntimeException();
    }

    public Take modifyCategory(Category newCategory) {
        return Take.builder()
                .category(newCategory)
                .takenYear(this.takenYear)
                .takenSemester(this.takenSemester)
                .member(this.member)
                .course(this.course)
                .customCourse(this.customCourse)
                .build();
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
